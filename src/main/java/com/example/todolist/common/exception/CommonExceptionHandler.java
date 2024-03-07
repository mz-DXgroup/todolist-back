package com.example.todolist.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({CustomException.class})
    protected ResponseEntity<ErrorDto> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(
                new ErrorDto(
                        ex.getExceptionStatus().getMessage(), ex.getExceptionStatus().getStatusCode()), HttpStatus.valueOf(ex.getExceptionStatus().getStatusCode()
        )
        );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<FieldError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(e.getBindingResult().getFieldErrors().get(0),
                // .get(0).getField() + "가  "+ e.getBindingResult().getFieldErrors().get(0).getDefaultMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    protected ResponseEntity<String> handlerEtcException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}