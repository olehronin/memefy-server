package com.mfy.memefy.dtos;

/**
 * The {@link ImgflipApiItem} class
 *
 * @author Oleh Ivasiuk
 */
public class ImgflipApiItem {
    private String id;
    private String name;
    private String url;
    private int width;
    private int height;
    private int box_count;
    private long captions;

    public ImgflipApiItem() {
    }

    public ImgflipApiItem(String id, String name, String url, int width, int height, int box_count, long captions) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.width = width;
        this.height = height;
        this.box_count = box_count;
        this.captions = captions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBox_count() {
        return box_count;
    }

    public void setBox_count(int box_count) {
        this.box_count = box_count;
    }

    public long getCaptions() {
        return captions;
    }

    public void setCaptions(long captions) {
        this.captions = captions;
    }
}
