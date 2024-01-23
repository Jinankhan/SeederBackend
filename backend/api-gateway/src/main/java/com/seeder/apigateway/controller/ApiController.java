package com.seeder.apigateway.controller;

import com.seeder.apigateway.payload.request.AuthenticationRequest;
import com.seeder.apigateway.payload.response.Token;
import com.seeder.apigateway.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class ApiController {

  @Autowired
  private ApiService apiService;

  @PostMapping("/login")
  public ResponseEntity<Token> loginUser(
    @RequestBody AuthenticationRequest authenticationRequest
  ) {
    return new ResponseEntity<>(
      apiService.loginUser(authenticationRequest),
      HttpStatus.OK
    );
  }
}
