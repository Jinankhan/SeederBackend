package com.seeder.paymentservice.exception;

import com.seeder.paymentservice.dto.response.ErrorResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleArgumentInvalid(
    MethodArgumentNotValidException e
  ) {
    Map<String, String> errors = new HashMap<>();

    e
      .getBindingResult()
      .getFieldErrors()
      .forEach(fieldError -> {
        String message = fieldError.getDefaultMessage();
        String field = fieldError.getField();
        errors.put(field, message);
      });

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PaymentsNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoPaymentsException(Exception e) {
    ErrorResponse errorResponse = new ErrorResponse();

    errorResponse.setMessage(e.getMessage());
    errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
    errorResponse.setTimeStamp(System.currentTimeMillis());

    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> handleHttpMessageNotReadableException(
    HttpMessageNotReadableException ex
  ) {
    String errorMessage = "Invalid JSON format.";
    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }
}
