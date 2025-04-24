package com.mfy.memefy.servise.impl;

import com.mfy.memefy.dtos.RedditApiResponse;
import com.mfy.memefy.entity.MemeEntity;
import com.mfy.memefy.servise.MemeApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * The {@link RedditMemeClient} class
 *
 * @author Oleh Ivasiuk
 */
@Service
public class RedditMemeClient implements MemeApiClient {
    private final RestTemplate restTemplate;
    private final Logger log = LoggerFactory.getLogger(RedditMemeClient.class);

    public RedditMemeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<MemeEntity> fetchMemes(String subreddit, int limit) {
        if (limit < 1 || limit > 50) {
            throw new IllegalArgumentException("Limit must be between 1 and 50");
        }
        if (subreddit == null || subreddit.trim().isEmpty() || !subreddit.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Invalid subreddit name: " + subreddit);
        }

        try {
            RedditApiResponse response = restTemplate.getForObject(
                    "https://meme-api.com/gimme/{subreddit}/{limit}",
                    RedditApiResponse.class,
                    subreddit,
                    limit
            );
            if (response == null || response.getMemes() == null) {
                log.warn("No memes returned from subreddit: {}", subreddit);
                return Collections.emptyList();
            }

            List<MemeEntity> memes = response.getMemes().stream()
                    .filter(meme -> meme.getPreview() != null && !meme.getPreview().isEmpty())
                    .map(meme -> new MemeEntity(
                            meme.getTitle(),
                            meme.getPreview().getLast(),
                            meme.getUps() != null ? meme.getUps() : 0L
                    ))
                    .toList();
            log.debug("Fetched {} memes from subreddit {}", memes.size(), subreddit);
            return memes;
        } catch (RestClientException e) {
            log.error("Failed to fetch memes from subreddit {}: {}", subreddit, e.getMessage());
            return Collections.emptyList();
        }
    }
}
