package com.MitoDev.FrostVault.exception;

import com.MitoDev.FrostVault.exception.custom.CustomException;
import com.MitoDev.FrostVault.model.dto.ExceptionDTO;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionDTO> exceptionHandler(CustomException ex){
        return ResponseEntity.status(ex.getCode()).body(new ExceptionDTO(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> exceptionHandler(MethodArgumentNotValidException ex){
        return ResponseEntity.badRequest().body(new ExceptionDTO(
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                        .collect(Collectors.joining("; "))
        ));
    }
}
