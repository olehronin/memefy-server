package com.mfy.memefy.servise.impl;

import com.mfy.memefy.dtos.MemeApiItem;
import com.mfy.memefy.dtos.MemeApiResponse;
import com.mfy.memefy.dtos.MemeDto;
import com.mfy.memefy.dtos.mappers.MemeMapper;
import com.mfy.memefy.entity.MemeEntity;
import com.mfy.memefy.repository.MemeRepository;
import com.mfy.memefy.servise.MemeService;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    private final MemeMapper memeMapper;
    private final Random random = new Random();
    private final RestTemplate restTemplate;

    public MemeServiceImpl(MemeRepository memeRepository, MemeMapper memeMapper) {
        this.memeRepository = memeRepository;
        this.memeMapper = memeMapper;
        this.restTemplate = new RestTemplate();
    }

    @PostConstruct
    public void init() {
//        memeRepository.deleteAll();

        MemeApiResponse response = restTemplate.getForObject(
                "https://meme-api.com/gimme/10",
                MemeApiResponse.class
        );

        if (response != null && response.getMemes() != null) {
            long id = 1;
            for (MemeApiItem apiMeme : response.getMemes()) {
                String imageUrl = apiMeme.getUrl();
                if (!imageUrl.endsWith(".jpg") && apiMeme.getPreview() != null) {
                    imageUrl = apiMeme.getPreview().stream()
                            .filter(url -> url.endsWith(".jpg"))
                            .findFirst()
                            .orElse(imageUrl);
                }

                String name = apiMeme.getTitle().length() > 100
                        ? apiMeme.getTitle().substring(0, 100)
                        : apiMeme.getTitle();
                long likes = random.nextLong(100);

                memeRepository.save(new MemeEntity(name, imageUrl, likes));
            }
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
    public PagedModel<MemeDto> getPageableMemes(Pageable pageable) {
        Page<MemeEntity> memes = memeRepository.findAll(pageable);
        Page<MemeDto> pollDtos = memes.map(memeMapper::toMemeDto);

        return new PagedModel<>(pollDtos);
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
