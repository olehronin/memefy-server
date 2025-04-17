package com.mfy.memefy.dtos;

/**
 * The {@link ImgflipApiResponse} class
 *
 * @author Oleh Ivasiuk
 */
public class ImgflipApiResponse {
    private boolean success;
    private ImgflipData data;

    public ImgflipApiResponse() {
    }

    public ImgflipApiResponse(boolean success, ImgflipData data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ImgflipData getData() {
        return data;
    }

    public void setData(ImgflipData data) {
        this.data = data;
    }
}
