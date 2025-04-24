package com.mfy.memefy.servise;

import com.mfy.memefy.entity.MemeEntity;

import java.util.List;

/**
 * The {@link MemeApiClient} interface
 *
 * @author Oleh Ivasiuk
 */
public interface MemeApiClient {

    List<MemeEntity> fetchMemes(String subreddit, int limit);
}
