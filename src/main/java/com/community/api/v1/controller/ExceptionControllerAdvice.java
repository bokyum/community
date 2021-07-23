package com.community.api.v1.controller;

import com.community.api.v1.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> illegalExHandle(IllegalArgumentException e) {
        log.error("[IllegalArgumentException] ex", e);
        return new ResponseEntity<>(new ResponseDto(null, e.getMessage()),
                HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> methodArgNotValidExHandle(MethodArgumentNotValidException e) {
        log.error("[MethodArgumentNotValidException] ex", e);
        return new ResponseEntity<>(new ResponseDto(null, "올바르지 않은 형식입니다."),
                HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> exHandle(Exception e) {
        log.error("[Exception] ex", e);
        return new ResponseEntity<>(new ResponseDto(null, e.getMessage()),
                HttpStatus.OK);
    }
}
