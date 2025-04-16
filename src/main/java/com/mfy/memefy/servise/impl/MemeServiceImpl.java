package com.mfy.memefy.servise.impl;

import com.mfy.memefy.entity.MemeEntity;
import com.mfy.memefy.repository.MemeRepository;
import com.mfy.memefy.servise.MemeService;
import jakarta.annotation.PostConstruct;
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
    private final MemeRepository memeRepository;
    private final Random random = new Random();

    public MemeServiceImpl(MemeRepository memeRepository) {
        this.memeRepository = memeRepository;
    }

    @PostConstruct
    public void init() {
        for (long i = 1; i <= 10; i++) {
            memeRepository.save(new MemeEntity(
                    "Meme " + i,
                    "https://test.com/meme" + i + ".jpg",
                    random.nextLong(100)
            ));
        }
    }

    @Override
    public List<MemeEntity> getAllMemes() {
        return memeRepository.findAll();
    }

    @Override
    public MemeEntity getMemeById(Long id) {
        return memeRepository.findById(id).orElse(null);
    }

    @Override
    public MemeEntity updateMeme(Long id, MemeEntity updatedMeme) {
        MemeEntity meme = memeRepository.findById(id).orElse(null);
        if (meme != null) {
            meme.setName(updatedMeme.getName());
            meme.setImageUrl(updatedMeme.getImageUrl());
            meme.setLikes(updatedMeme.getLikes());
            return memeRepository.save(meme);
        }
        return null;
    }
}
