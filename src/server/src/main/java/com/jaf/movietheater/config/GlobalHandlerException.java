package com.jaf.movietheater.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jaf.movietheater.exceptions.ResourceNotFoundException;
import com.jaf.movietheater.response.ResponseError;

@ControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        ResponseError responseError = new ResponseError(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException e) {
        ResponseError responseError = new ResponseError(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }
}
