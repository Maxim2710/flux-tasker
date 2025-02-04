package com.fluxtasker.exception.handler;

import com.fluxtasker.bom.ErrorResponse;
import com.fluxtasker.exception.CommentCreationException;
import com.fluxtasker.exception.TaskCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskCreationException.class)
    public ResponseEntity<ErrorResponse> handlerTaskCreationException(TaskCreationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(CommentCreationException.class)
    public ResponseEntity<ErrorResponse> handlerCommentCreationException(CommentCreationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ex.getMessage()));
    }
}
