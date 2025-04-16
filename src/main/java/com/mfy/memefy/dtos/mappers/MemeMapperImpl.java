package com.mfy.memefy.dtos.mappers;

import com.mfy.memefy.dtos.MemeDto;
import com.mfy.memefy.entity.MemeEntity;
import org.springframework.stereotype.Component;

/**
 * The {@link MemeMapperImpl} class
 *
 * @author Oleh Ivasiuk
 */
@Component
public class MemeMapperImpl implements MemeMapper {

    @Override
    public MemeDto toMemeDto(MemeEntity memeEntity) {
        if (memeEntity == null) {
            return null;
        }

        MemeDto pollDto = new MemeDto();
        pollDto.setId(memeEntity.getId());
        pollDto.setName(memeEntity.getName());
        pollDto.setImageUrl(memeEntity.getImageUrl());
        pollDto.setLikes(memeEntity.getLikes());

        return pollDto;
    }
}
