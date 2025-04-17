package com.mfy.memefy.dtos;

import java.util.List;

/**
 * The {@link ImgflipData} class
 *
 * @author Oleh Ivasiuk
 */
public class ImgflipData {
    private List<ImgflipApiItem> memes;

    public ImgflipData() {
    }

    public ImgflipData(List<ImgflipApiItem> memes) {
        this.memes = memes;
    }

    public List<ImgflipApiItem> getMemes() {
        return memes;
    }

    public void setMemes(List<ImgflipApiItem> memes) {
        this.memes = memes;
    }
}
