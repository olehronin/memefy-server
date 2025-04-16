package com.mfy.memefy.dtos;

import java.util.Objects;

/**
 * The {@link MemeDto} class
 *
 * @author Oleh Ivasiuk
 */
public class MemeDto {
    private String id;
    private String name;
    private String imageUrl;
    private String likes;

    public MemeDto() {
    }

    public MemeDto(String id, String name, String imageUrl, String likes) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.likes = likes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemeDto memeDto = (MemeDto) o;
        return Objects.equals(id, memeDto.id) && Objects.equals(name, memeDto.name) && Objects.equals(imageUrl, memeDto.imageUrl) && Objects.equals(likes, memeDto.likes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageUrl, likes);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MemeDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", imageUrl='").append(imageUrl).append('\'');
        sb.append(", likes='").append(likes).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
