package com.mfy.memefy.servise;

import com.mfy.memefy.dtos.MemeDto;
import com.mfy.memefy.entity.MemeEntity;

import java.util.List;

/**
 * The {@link MemeService} class
 *
 * @author Oleh Ivasiuk
 */
public interface MemeService {

    List<MemeDto> getAllMemes(boolean useImg);

    MemeDto getMemeById(Long id);

    MemeDto updateMeme(Long id, MemeDto newMeme);

    MemeEntity getMemeEntityById(Long id);
}
