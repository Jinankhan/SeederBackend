package com.seederuserservice.exception;

import com.seederuserservice.dto.response.ErrorResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    ErrorResponse errorResponse = new ErrorResponse();

    errorResponse.setMessage(e.getMessage());
    errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
    errorResponse.setTimeStamp(System.currentTimeMillis());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleUserException(Exception e) {
    ErrorResponse errorResponse = new ErrorResponse();

    errorResponse.setMessage(e.getMessage());
    errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
    errorResponse.setTimeStamp(System.currentTimeMillis());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleInvalidArgumentException(
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

    return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
  }
}
