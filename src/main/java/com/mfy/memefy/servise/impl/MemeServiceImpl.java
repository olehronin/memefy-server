package com.mfy.memefy.servise.impl;

import com.mfy.memefy.dtos.MemeDto;
import com.mfy.memefy.dtos.mappers.MemeMapper;
import com.mfy.memefy.entity.MemeEntity;
import com.mfy.memefy.repository.MemeRepository;
import com.mfy.memefy.servise.MemeApiClient;
import com.mfy.memefy.servise.MemeService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * The {@link MemeServiceImpl} class
 *
 * @author Oleh Ivasiuk
 */
@Service
public class MemeServiceImpl implements MemeService {
    private static final int MAX_MEMES = 10;
    private static final int MAX_NAME_LENGTH = 100;

    private final MemeRepository memeRepository;
    private final MemeMapper memeMapper;
    private final MemeApiClient imgflipClient;
    private final MemeApiClient redditClient;

    @Value("${meme.api.use-img:true}")
    private boolean useImgByDefault;

    public MemeServiceImpl(
            MemeRepository memeRepository,
            MemeMapper memeMapper,
            ImgflipMemeClient imgflipClient,
            RedditMemeClient redditClient
    ) {
        this.memeRepository = memeRepository;
        this.memeMapper = memeMapper;
        this.imgflipClient = imgflipClient;
        this.redditClient = redditClient;
    }

    @PostConstruct
    public void init() {
        fetchPosts(useImgByDefault);
    }

    @Override
    public List<MemeDto> getAllMemes(boolean useImg) {
        fetchPosts(useImg);
        return memeRepository.findAll().stream()
                .map(memeMapper::toMemeDto)
                .toList();
    }

    @Override
    public MemeDto getMemeById(Long id) {
        return memeMapper.toMemeDto(getMemeEntityById(id));
    }

    @Override
    public MemeDto updateMeme(Long id, MemeDto newMeme) {
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

    private void fetchPosts(boolean useImg) {
        memeRepository.deleteAll();
        memeRepository.resetSequence();

        List<MemeEntity> memes = useImg
                ? imgflipClient.fetchMemes(MAX_MEMES)
                : redditClient.fetchMemes(MAX_MEMES);
        memeRepository.saveAll(memes);
    }

    private void validateMemeDto(MemeDto meme) {
        if (meme.getName() == null || meme.getName().length() < 3 || meme.getName().length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Name must be 3–100 characters");
        }
        if (meme.getImageUrl() == null || !meme.getImageUrl().matches("^https?://.*\\.jpg$")) {
            throw new IllegalArgumentException("Image URL must be a valid JPG link");
        }
        if (meme.getLikes() < 0 || meme.getLikes() > 99) {
            throw new IllegalArgumentException("Likes must be between 0 and 99");
        }
    }
}
