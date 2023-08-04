package com.mutsa.sns.global.error;

import com.mutsa.sns.global.common.ResponseDto;
import com.mutsa.sns.global.error.status.Status400Exception;
import com.mutsa.sns.global.error.status.Status403Exception;
import com.mutsa.sns.global.error.status.Status404Exception;
import com.mutsa.sns.global.error.status.Status500Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    // 400에러 처리
    @ExceptionHandler(Status400Exception.class)
    public ResponseEntity<ResponseDto> handle400(Status400Exception exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto(exception.getMessage()));
    }

    // 403에러 처리
    @ExceptionHandler(Status403Exception.class)
    public ResponseEntity<ResponseDto> handle403(Status403Exception exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ResponseDto(exception.getMessage()));
    }

    // 404에러 처리
    @ExceptionHandler(Status404Exception.class)
    public ResponseEntity<ResponseDto> handle404(Status404Exception exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseDto(exception.getMessage()));
    }

    // 500에러 처리
    @ExceptionHandler(Status500Exception.class)
    public ResponseEntity<ResponseDto> handle500(Status500Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto(exception.getMessage()));
    }
}
