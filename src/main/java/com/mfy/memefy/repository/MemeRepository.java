package com.mfy.memefy.repository;

import com.mfy.memefy.entity.MemeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@link MemeRepository} interface
 *
 * @author Oleh Ivasiuk
 */
public interface MemeRepository extends JpaRepository<MemeEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE memes ALTER COLUMN id RESTART WITH 1", nativeQuery = true)
    void resetSequence();
}
