package com.mfy.memefy.repository;

import com.mfy.memefy.entity.MemeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

/**
 * The {@link MemeRepository} interface
 *
 * @author Oleh Ivasiuk
 */
public interface MemeRepository extends JpaRepository<MemeEntity, Long> {

    @Query("SELECT m.imageUrl FROM MemeEntity m")
    Set<String> findAllImageUrls();

}
