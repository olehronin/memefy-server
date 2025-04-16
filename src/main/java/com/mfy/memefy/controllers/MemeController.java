package com.mfy.memefy.controllers;

import com.mfy.memefy.domain.Response;
import com.mfy.memefy.dtos.MemeDto;
import com.mfy.memefy.entity.MemeEntity;
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

    @GetMapping
    public List<MemeEntity> getAllMemes() {
        return memeService.getAllMemes();
    }

    @GetMapping({"/pageable"})
    public ResponseEntity<Response> getManyMemes(
            @PageableDefault(direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request) {

        try {
            PagedModel<MemeDto> pageableMemes = memeService.getPageableMemes(pageable);
            return ResponseEntity.ok(RequestUtils.getResponse(request, pageableMemes,
                    "Pagination successful", HttpStatus.OK));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(RequestUtils.getResponse(
                    request, Collections.emptyMap(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
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
