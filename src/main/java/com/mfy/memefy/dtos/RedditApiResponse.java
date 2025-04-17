package com.mfy.memefy.dtos;

import java.util.List;

/**
 * The {@link RedditApiResponse} class
 *
 * @author Oleh Ivasiuk
 */
public class RedditApiResponse {
    private int count;
    private List<RedditMemeApiItem> memes;

    public RedditApiResponse() {
    }

    public RedditApiResponse(int count, List<RedditMemeApiItem> memes) {
        this.count = count;
        this.memes = memes;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<RedditMemeApiItem> getMemes() {
        return memes;
    }

    public void setMemes(List<RedditMemeApiItem> memes) {
        this.memes = memes;
    }
}
