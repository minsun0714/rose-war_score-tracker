// UserExceptionHandler.java
package com.rosewar.scoretracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    // UsernameNotFoundException 처리
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UsernameNotFoundException ex) {
        return new ResponseEntity<>("사용자를 찾을 수 없습니다: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // UserNotAuthenticatedException 처리
    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<String> handleUserNotAuthenticatedException(UserNotAuthenticatedException ex) {
        return new ResponseEntity<>("인증되지 않은 사용자입니다: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
