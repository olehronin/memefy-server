package com.mfy.memefy.servise.impl;

import com.mfy.memefy.dtos.MemeDto;
import com.mfy.memefy.dtos.mappers.MemeMapper;
import com.mfy.memefy.entity.MemeEntity;
import com.mfy.memefy.repository.MemeRepository;
import com.mfy.memefy.servise.MemeService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@link MemeServiceImpl} class
 *
 * @author Oleh Ivasiuk
 */
@Service
public class MemeServiceImpl implements MemeService {
    public static final int MAX_NAME_LENGTH = 1024;

    private final MemeRepository memeRepository;
    private final MemeMapper memeMapper;
    private final RedditMemeClient redditMemeClient;
    private final List<String> subreddits;
    private final int maxMemes;
    private final int redditFetchLimit;
    private final Logger log = LoggerFactory.getLogger(MemeServiceImpl.class);

    public MemeServiceImpl(
            MemeRepository memeRepository,
            MemeMapper memeMapper,
            RedditMemeClient redditMemeClient,
            @Value("${memefy.subreddits:wholesomememes,memes,meme,dankmemes,PrequelMemes}") String[] subreddits,
            @Value("${memefy.max-memes:100}") int maxMemes,
            @Value("${memefy.reddit-fetch-limit:50}") int redditFetchLimit) {
        this.memeRepository = memeRepository;
        this.memeMapper = memeMapper;
        this.redditMemeClient = redditMemeClient;
        this.subreddits = Arrays.asList(subreddits);
        this.maxMemes = maxMemes;
        this.redditFetchLimit = redditFetchLimit;
    }

    @PostConstruct
    public void init() {
        fetchAndSaveMemes();
    }

    @Override
    public PagedModel<MemeDto> getPageableMemes(Pageable pageable) {
        Page<MemeEntity> memes = memeRepository.findAll(pageable);
        Page<MemeDto> memeDtos = memes.map(memeMapper::toMemeDto);
        return new PagedModel<>(memeDtos);
    }

    @Override
    public MemeDto getMemeById(Long id) {
        return memeMapper.toMemeDto(getMemeEntityById(id));
    }

    @Override
    public MemeDto patchMeme(Long id, MemeDto newMeme) {
        validateMemeDto(newMeme);

        MemeEntity meme = getMemeEntityById(id);
        meme.setName(newMeme.getName());
        meme.setImageUrl(newMeme.getImageUrl());
        meme.setLikes(newMeme.getLikes());

        MemeEntity updated = memeRepository.save(meme);
        return memeMapper.toMemeDto(updated);
    }

    @Override
    public MemeEntity getMemeEntityById(Long id) {
        return memeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meme Entity with id `%s` not found".formatted(id)));
    }

    private void fetchAndSaveMemes() {
        long startTime = System.currentTimeMillis();
        long currentCount = memeRepository.count();
        if (currentCount >= maxMemes) {
            log.info("Database already contains {} memes, skipping initialization", currentCount);
            return;
        }

        int memesToFetch = (int) (maxMemes - currentCount);
        log.info("Need to fetch {} memes", memesToFetch);

        Set<String> existingImageUrls = memeRepository.findAllImageUrls();
        Set<MemeEntity> allFetchedMemes = new HashSet<>();

        for (String subreddit : subreddits) {
            if (allFetchedMemes.size() >= memesToFetch) {
                break;
            }

            log.debug("Fetching memes from subreddit: {}", subreddit);
            List<MemeEntity> fetchedMemes = redditMemeClient.fetchMemes(subreddit, redditFetchLimit);
            List<MemeEntity> uniqueMemes = filterUniqueMemes(fetchedMemes, existingImageUrls, allFetchedMemes);
            allFetchedMemes.addAll(uniqueMemes);
            log.info("Fetched {} unique memes from subreddit {}", uniqueMemes.size(), subreddit);
        }

        List<MemeEntity> memesToSave = allFetchedMemes.stream()
                .limit(memesToFetch)
                .toList();
        memeRepository.saveAll(memesToSave);
        log.info("Saved {} memes to database in {}ms", memesToSave.size(), System.currentTimeMillis() - startTime);

        if (memesToSave.size() < memesToFetch) {
            log.warn("Could only fetch {} memes out of requested {}", memesToSave.size(), memesToFetch);
        }
    }

    private List<MemeEntity> filterUniqueMemes(
            List<MemeEntity> fetchedMemes,
            Set<String> existingImageUrls,
            Set<MemeEntity> allFetchedMemes) {
        return fetchedMemes.stream()
                .filter(meme -> !existingImageUrls.contains(meme.getImageUrl()))
                .filter(meme -> !allFetchedMemes.contains(meme))
                .toList();
    }

    private void validateMemeDto(MemeDto meme) {
        if (meme.getName() == null || meme.getName().length() < 3 || meme.getName().length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Name must be 3–1024 characters");
        }
        if (meme.getImageUrl() == null || !meme.getImageUrl().matches("^https?://.*\\.(jpg|jpeg|gif|png)(\\?.*)?$")) {
            throw new IllegalArgumentException("Image URL must be a valid JPG, JPEG, GIF, or PNG link");
        }
        if (meme.getLikes() < 0) {
            throw new IllegalArgumentException("Likes should not be less than 0");
        }
    }
}