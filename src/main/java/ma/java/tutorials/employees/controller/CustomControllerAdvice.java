package ma.java.tutorials.employees.controller;

import ma.java.tutorials.employees.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException businessException){
        return new ResponseEntity<>(businessException.getMessage(), businessException.getHttpStatus());
    }
}
