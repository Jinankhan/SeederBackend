package com.seeder.apigateway.service;

import com.seeder.apigateway.payload.request.AuthenticationRequest;
import com.seeder.apigateway.payload.response.Token;

public interface IApiService {
  Token loginUser(AuthenticationRequest authenticationRequest);
}
