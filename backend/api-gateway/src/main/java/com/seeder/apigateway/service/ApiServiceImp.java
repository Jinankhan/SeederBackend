package com.seeder.apigateway.service;

import com.seeder.apigateway.payload.User;
import com.seeder.apigateway.payload.request.AuthenticationRequest;
import com.seeder.apigateway.payload.response.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ApiServiceImp implements IApiService {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;


  @Autowired
  ApiServiceImp(
          AuthenticationManager authenticationManager,
          JwtService jwtService
  ) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  public Token loginUser(AuthenticationRequest authenticationRequest) {
    RestTemplate restTemplate = new RestTemplate();
    try {

      User user = restTemplate.getForObject(
              "http://localhost:9001/api/v1/users/email?email=" +
                      authenticationRequest.getEmail(),
              User.class
      );
      log.info("User retrieved: {}", user);

      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      authenticationRequest.getEmail(),
                      user.getPassword()
              )
      );
      log.info("Authentication successful for user: {}", user.getEmail());

      String jwtToken = jwtService.generateToken(user);
      Token token = new Token();
      token.setToken(jwtToken);

      log.info("JWT Token generated for user: {}", user.getEmail());

      return token;
    }  catch (HttpClientErrorException.NotFound ex)  {
      throw new UsernameNotFoundException("User not found", ex);
    }  catch (Exception e) {
      log.error("Error retrieving or authenticating user", e);
      throw new BadCredentialsException("Invalid credentials", e);
    }
  }
}
