package com.seeder.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

  @Bean
  public RestTemplate template() {
    return new RestTemplate();
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    return new AuthenticationManager() {

      @Override
      public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
      }
    };
  }
}
