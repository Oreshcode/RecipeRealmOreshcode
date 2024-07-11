package com.RecipeRealmOreshcode.advice;

import com.RecipeRealmOreshcode.advice.exception.EmailAlreadyExistException;
import com.RecipeRealmOreshcode.advice.exception.RecordNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Operation(summary = "Handles RecordNotFoundException", description = "Returned when a requested record is not found in the database.")
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<?> handleRecordNotFoundException(RecordNotFoundException recordNotFoundException){
        return new ResponseEntity<>(recordNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Handles MethodArgumentNotValidException", description = "Returned when method's arguments are not valid.")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?>handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String,String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName,message);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Handles EmailAlreadyExistsException", description = "Returned when a requested email already exists.")
    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<?> handleEmailAlreadyExistException(EmailAlreadyExistException emailAlreadyExistException){
        return new ResponseEntity<>(emailAlreadyExistException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}