package com.mfy.memefy.dtos.mappers;

import com.mfy.memefy.dtos.MemeDto;
import com.mfy.memefy.entity.MemeEntity;

/**
 * The {@link MemeMapper} class
 *
 * @author Oleh Ivasiuk
 */
public interface MemeMapper {

    MemeDto toMemeDto(MemeEntity memeEntity);
}
