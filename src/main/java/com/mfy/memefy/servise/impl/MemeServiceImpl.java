package com.mfy.memefy.servise.impl;

import com.mfy.memefy.dtos.MemeDto;
import com.mfy.memefy.dtos.mappers.MemeMapper;
import com.mfy.memefy.entity.MemeEntity;
import com.mfy.memefy.repository.MemeRepository;
import com.mfy.memefy.servise.MemeApiClient;
import com.mfy.memefy.servise.MemeService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The {@link MemeServiceImpl} class
 *
 * @author Oleh Ivasiuk
 */
@Service
public class MemeServiceImpl implements MemeService {
    private static final int MAX_MEMES = 10;
    private static final int MAX_NAME_LENGTH = 100;
    public static final String IMGFLIP = "IMGFLIP";

    private final MemeRepository memeRepository;
    private final MemeMapper memeMapper;
    private final MemeApiClient imgflipClient;

    public MemeServiceImpl(
            MemeRepository memeRepository,
            MemeMapper memeMapper,
            ImgflipMemeClient imgflipClient
    ) {
        this.memeRepository = memeRepository;
        this.memeMapper = memeMapper;
        this.imgflipClient = imgflipClient;
    }

    @PostConstruct
    public void init() {
        fetchPostsIfEmpty();
    }

    @Override
    public PagedModel<MemeDto> getPageableMemes(Pageable pageable) {
        fetchPostsIfEmpty();

        Page<MemeEntity> memes = memeRepository.findAll(pageable);
        Page<MemeDto> pollDtos = memes.map(memeMapper::toMemeDto);
        return new PagedModel<>(pollDtos);
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

    private void fetchPostsIfEmpty() {
        if (memeRepository.countBySource(IMGFLIP) >= MAX_MEMES) {
            return;
        }

        List<MemeEntity> memes = imgflipClient.fetchMemes(MAX_MEMES);
        memes.forEach(meme -> meme.setSource(IMGFLIP));

        List<MemeEntity> newMemes = memes.stream()
                .filter(meme -> !memeRepository.existsByImageUrl(meme.getImageUrl()))
                .toList();

        memeRepository.saveAll(newMemes);
    }

    private void validateMemeDto(MemeDto meme) {
        if (meme.getName() == null || meme.getName().length() < 3 || meme.getName().length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Name must be 3–100 characters");
        }
        if (meme.getImageUrl() == null || !meme.getImageUrl().matches("^https?://.*\\.(jpg|jpeg)(\\?.*)?$")) {
            throw new IllegalArgumentException("Image URL must be a valid JPG or JPEG link");
        }
        if (meme.getLikes() < 0 || meme.getLikes() > 99) {
            throw new IllegalArgumentException("Likes must be between 0 and 99");
        }
    }
}
