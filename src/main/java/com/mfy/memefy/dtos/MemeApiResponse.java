package com.mfy.memefy.dtos;

import java.util.List;

/**
 * The {@link MemeApiResponse} class
 *
 * @author Oleh Ivasiuk
 */
public class MemeApiResponse {
    private int count;
    private List<MemeApiItem> memes;

    public MemeApiResponse() {
    }

    public MemeApiResponse(int count, List<MemeApiItem> memes) {
        this.count = count;
        this.memes = memes;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MemeApiItem> getMemes() {
        return memes;
    }

    public void setMemes(List<MemeApiItem> memes) {
        this.memes = memes;
    }
}
