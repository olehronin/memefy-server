package com.mfy.memefy.utils;

import com.mfy.memefy.domain.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * The {@link RequestUtils} class
 *
 * @author Oleh Ivasiuk
 */
public class RequestUtils {

    public static Response getResponse(HttpServletRequest request, Map<?, ?> data, String message, HttpStatus status) {
        return new Response(
                LocalDateTime.now().toString(),
                status.value(),
                request.getRequestURI(),
                HttpStatus.valueOf(status.value()),
                message,
                StringUtils.EMPTY,
                data);
    }

    public static Response getResponse(HttpServletRequest request, Object data, String message, HttpStatus status) {
        return new Response(
                LocalDateTime.now().toString(),
                status.value(),
                request.getRequestURI(),
                HttpStatus.valueOf(status.value()),
                message,
                StringUtils.EMPTY,
                data);
    }
}
