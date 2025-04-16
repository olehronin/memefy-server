package com.mfy.memefy.controllers;

import com.mfy.memefy.entity.MemeEntity;
import com.mfy.memefy.servise.MemeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The {@link MemeController} class
 *
 * @author Oleh Ivasiuk
 */

@RestController
@RequestMapping("/api/memes")
public class MemeController {
    private final MemeService memeService;

    public MemeController(MemeService memeService) {
        this.memeService = memeService;
    }

    @GetMapping
    public List<MemeEntity> getAllMemes() {
        return memeService.getAllMemes();
    }

    @GetMapping("/{id}")
    public MemeEntity getMemeById(@PathVariable Long id) {
        return memeService.getMemeById(id);
    }

    @PutMapping("/{id}")
    public MemeEntity updateMeme(@PathVariable Long id, @RequestBody MemeEntity meme) {
        return memeService.updateMeme(id, meme);
    }
}
