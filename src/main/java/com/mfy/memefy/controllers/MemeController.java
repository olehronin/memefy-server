package com.mfy.memefy.controllers;

import com.mfy.memefy.domain.Response;
import com.mfy.memefy.dtos.MemeDto;
import com.mfy.memefy.servise.MemeService;
import com.mfy.memefy.utils.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * The {@link MemeController} class
 *
 * @author Oleh Ivasiuk
 */
@RestController
@RequestMapping("/api/v1/memes")
public class MemeController {
    private final MemeService memeService;

    public MemeController(MemeService memeService) {
        this.memeService = memeService;
    }

    @GetMapping()
    public ResponseEntity<Response> getAllMemes(
            @RequestParam(defaultValue = "true") boolean useImg,
            HttpServletRequest request) {
        try {
            List<MemeDto> memes = memeService.getAllMemes(useImg);
            return ResponseEntity.ok(RequestUtils.getResponse(request, memes,
                    "Memes found successfully", HttpStatus.OK)
            );
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(RequestUtils.getResponse(
                    request, Collections.emptyMap(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/{id}")
    public MemeDto getMemeById(@PathVariable Long id) {
        return memeService.getMemeById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response> patchMeme(@PathVariable Long id, @RequestBody MemeDto meme, HttpServletRequest request) {
        try {
            MemeDto updatedMeme = memeService.patchMeme(id, meme);
            return ResponseEntity.ok(RequestUtils.getResponse(request, updatedMeme,
                    "Meme successfully updated", HttpStatus.OK)
            );
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(RequestUtils.getResponse(
                    request, Collections.emptyMap(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}
