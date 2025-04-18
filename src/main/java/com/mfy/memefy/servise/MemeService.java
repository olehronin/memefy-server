package com.mfy.memefy.servise;

import com.mfy.memefy.dtos.MemeDto;
import com.mfy.memefy.entity.MemeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

/**
 * The {@link MemeService} class
 *
 * @author Oleh Ivasiuk
 */
public interface MemeService {

    PagedModel<MemeDto> getPageableMemes(Pageable pageable);

    MemeDto getMemeById(Long id);

    MemeDto patchMeme(Long id, MemeDto meme);

    MemeEntity getMemeEntityById(Long id);
}
