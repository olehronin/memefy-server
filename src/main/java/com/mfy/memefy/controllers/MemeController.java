package com.mfy.memefy.controllers;

import com.mfy.memefy.domain.Response;
import com.mfy.memefy.dtos.MemeDto;
import com.mfy.memefy.servise.MemeService;
import com.mfy.memefy.utils.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Response> getPageableMemes(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request) {
        PagedModel<MemeDto> pageableMemes = memeService.getPageableMemes(pageable);
        return ResponseEntity.ok(RequestUtils.getResponse(request, pageableMemes,
                "Memes found successfully", HttpStatus.OK));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getMemeById(@PathVariable Long id, HttpServletRequest request) {
        MemeDto meme = memeService.getMemeById(id);
        return ResponseEntity.ok(RequestUtils.getResponse(request, meme,
                "Meme found successfully", HttpStatus.OK));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response> patchMeme(@PathVariable Long id, @RequestBody MemeDto meme, HttpServletRequest request) {
        MemeDto updatedMeme = memeService.patchMeme(id, meme);
        return ResponseEntity.ok(RequestUtils.getResponse(request, updatedMeme,
                "Meme successfully updated", HttpStatus.OK));
    }
}
