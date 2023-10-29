package com.MitoDev.FrostVault.exception;

import com.MitoDev.FrostVault.model.dto.ExceptionDTO;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDTO> exceptionHandler(RuntimeException ex){
        return ResponseEntity.badRequest().body(new ExceptionDTO(ex.getMessage()));
    }
}
