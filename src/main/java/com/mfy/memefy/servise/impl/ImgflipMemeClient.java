package com.mfy.memefy.servise.impl;

import com.mfy.memefy.dtos.ImgflipApiResponse;
import com.mfy.memefy.entity.MemeEntity;
import com.mfy.memefy.servise.MemeApiClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The {@link ImgflipMemeClient} class
 *
 * @author Oleh Ivasiuk
 */
@Service
public class ImgflipMemeClient implements MemeApiClient {
    private final RestTemplate restTemplate;
    private final Random random = new Random();

    public ImgflipMemeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<MemeEntity> fetchMemes(int limit) {
        ImgflipApiResponse response = restTemplate.getForObject("https://api.imgflip.com/get_memes", ImgflipApiResponse.class);
        if (response == null || !response.isSuccess()) return Collections.emptyList();

        return response.getData().getMemes().stream()
                .filter(m -> m.getUrl().endsWith(".jpg"))
                .limit(limit)
                .map(m -> new MemeEntity(
                        truncate(m.getName(), 100),
                        m.getUrl(),
                        random.nextLong(100)))
                .toList();
    }

    private String truncate(String str, int maxLength) {
        return str.length() > maxLength ? str.substring(0, maxLength) : str;
    }
}
