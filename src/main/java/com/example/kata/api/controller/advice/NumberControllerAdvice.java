package com.example.kata.api.controller.advice;

import com.example.kata.api.controller.NumberController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice(assignableTypes = NumberController.class)
public class NumberControllerAdvice {

    /**
     * handle a non valid integer
     *
     * @param ex method argument type mismatch exception
     * @return bad request status with explicit message
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body("Le paramètre 'number' doit être un entier valide");
    }

    /**
     * handle empty or null number entry
     *
     * @param ex missing param exception
     * @return bad request status with message
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParam(MissingServletRequestParameterException ex) {
        return ResponseEntity.badRequest().body("Le paramètre '" + ex.getParameterName() + "' est requis et ne peut être null");
    }

    /**
     * handle thrown exception for range limit
     *
     * @param ex illegal argument execption
     * @return bad request and a message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
