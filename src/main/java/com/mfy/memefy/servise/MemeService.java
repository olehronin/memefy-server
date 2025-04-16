package com.mfy.memefy.servise;

import com.mfy.memefy.entity.MemeEntity;

import java.util.List;

/**
 * The {@link MemeService} class
 *
 * @author Oleh Ivasiuk
 */
public interface MemeService {

    List<MemeEntity> getAllMemes();

    MemeEntity getMemeById(Long id);

    MemeEntity updateMeme(Long id, MemeEntity updatedMeme);
}
