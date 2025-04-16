package com.mfy.memefy.servise;

import com.mfy.memefy.dtos.MemeDto;
import com.mfy.memefy.entity.MemeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

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

    PagedModel<MemeDto> getPageableMemes(Pageable pageable);
}
