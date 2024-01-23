package com.seeder.apigateway.service;

import com.seeder.apigateway.payload.User;
import com.seeder.apigateway.payload.request.AuthenticationRequest;
import com.seeder.apigateway.payload.response.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  public Token loginUser(AuthenticationRequest authenticationRequest) {
    RestTemplate restTemplate = new RestTemplate();
    User user = restTemplate.getForObject(
      "http://localhost:9001/api/v1/users/email?email=" +
      authenticationRequest.getEmail(),
      User.class
    );
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        authenticationRequest.getEmail(),
        user.getPassword()
      )
    );
    String jwtToken = jwtService.generateToken(user);
    Token token = new Token();
    token.setToken(jwtToken);
    return token;
  }
}
