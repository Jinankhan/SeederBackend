package com.seeder.cashkickservice.exception;

import com.seeder.cashkickservice.dto.response.ErrorResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
    Exception e
  ) {
    ErrorResponse errorResponse = new ErrorResponse();

    errorResponse.setStatus(HttpStatus.NOT_FOUND.value());

    errorResponse.setMessage(e.getMessage());

    errorResponse.setTimeStamp(System.currentTimeMillis());

    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleArgumentInvalid(
    MethodArgumentNotValidException e
  ) {
    Map<String, String> errors = new HashMap<>();

    e
      .getBindingResult()
      .getAllErrors()
      .forEach(error -> {
        String message = error.getDefaultMessage();
        String field = ((FieldError) error).getField();
        errors.put(field, message);
      });

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
}
