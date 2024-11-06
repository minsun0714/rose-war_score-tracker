package com.rosewar.scoretracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionErrorHandler {

    // 예시: NullPointerException 처리
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        return new ResponseEntity<>("Null Pointer Exception 발생: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 예시: IllegalArgumentException 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>("잘못된 입력: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 예시: 기타 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("서버 오류 발생: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
