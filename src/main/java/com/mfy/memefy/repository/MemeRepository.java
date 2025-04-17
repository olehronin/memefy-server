package com.mfy.memefy.repository;

import com.mfy.memefy.entity.MemeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The {@link MemeRepository} interface
 *
 * @author Oleh Ivasiuk
 */
public interface MemeRepository extends JpaRepository<MemeEntity, Long> {

    int countBySource(String source);

    boolean existsByImageUrl(String imageUrl);

    List<MemeEntity> findAllBySource(String source);
}
