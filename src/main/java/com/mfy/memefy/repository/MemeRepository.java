package com.mfy.memefy.repository;

import com.mfy.memefy.entity.MemeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The {@link MemeRepository} interface
 *
 * @author Oleh Ivasiuk
 */
public interface MemeRepository extends JpaRepository<MemeEntity, Long> {

    boolean existsByImageUrl(String imageUrl);
}
