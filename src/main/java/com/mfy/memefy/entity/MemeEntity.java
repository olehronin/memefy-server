package com.mfy.memefy.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mfy.memefy.entity.base.BaseAuditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * The {@link MemeEntity} class
 *
 * @author Oleh Ivasiuk
 */
@Entity
@Table(name = "memes")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MemeEntity extends BaseAuditable {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "image_url", nullable = false, length = 512)
    private String imageUrl;

    @Column(name = "likes", nullable = false)
    private Long likes;

    public MemeEntity() {
    }

    public MemeEntity(String name, String imageUrl, Long likes) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }
}
