package com.mfy.memefy.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public record Response(String time,
                       int code,
                       String path,
                       HttpStatus status,
                       String message,
                       String exception,
                       Object data) {
}