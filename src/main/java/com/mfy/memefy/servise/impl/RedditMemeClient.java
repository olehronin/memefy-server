package com.mfy.memefy.servise.impl;

import com.mfy.memefy.dtos.RedditApiResponse;
import com.mfy.memefy.entity.MemeEntity;
import com.mfy.memefy.servise.MemeApiClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The {@link RedditMemeClient} class
 *
 * @author Oleh Ivasiuk
 */
@Service
public class RedditMemeClient implements MemeApiClient {
    private final RestTemplate restTemplate;
    private final Random random = new Random();

    public RedditMemeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<MemeEntity> fetchMemes(int limit) {
        RedditApiResponse response = restTemplate.getForObject("https://meme-api.com/gimme/wholesomememes/" + limit, RedditApiResponse.class);
        if (response == null || response.getMemes() == null) return Collections.emptyList();

        return response.getMemes().stream()
                .map(m -> new MemeEntity(
                        truncate(m.getTitle(), 100),
                        m.getUrl(),
                        random.nextLong(100)))
                .toList();
    }

    private String truncate(String str, int maxLength) {
        return str.length() > maxLength ? str.substring(0, maxLength) : str;
    }
}
