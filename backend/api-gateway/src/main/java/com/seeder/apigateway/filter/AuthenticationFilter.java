package com.seeder.apigateway.filter;

import com.seeder.apigateway.exception.AccessDeniedException;
import com.seeder.apigateway.payload.response.UserResponse;
import com.seeder.apigateway.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

@Component
@Slf4j
public class AuthenticationFilter
  extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

  private final RouteValidator validator;

  private final RestTemplate template;

  @Autowired
  private EurekaDiscoveryClient discoveryClient;

  @Autowired
  private JwtService jwtService;

  @Autowired
  public AuthenticationFilter(
    RouteValidator validator,
    RestTemplate template,
    JwtService jwtService
  ) {
    super(Config.class);
    this.validator = validator;
    this.template = template;
    this.jwtService = jwtService;
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (
      (exchange, chain) -> {
        if (validator.isSecured.test(exchange.getRequest())) {
          if (
            !exchange
              .getRequest()
              .getHeaders()
              .containsKey(HttpHeaders.AUTHORIZATION)
          ) {
            log.error("Missing authorization header");
            throw new AccessDeniedException("missing authorization header");
          }

          String authHeader = exchange
            .getRequest()
            .getHeaders()
            .get(HttpHeaders.AUTHORIZATION)
            .get(0);
          if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader = authHeader.substring(7);
          }

          try {
            log.info("Validating token...");
            ServerWebExchange newExchange = validateTokenAndAddHeader(
              exchange,
              authHeader
            );
            log.info("Token validation successful");
            return chain.filter(newExchange);
          } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            throw new AccessDeniedException(
              "unauthorized access to application"
            );
          }
        }
        return chain.filter(exchange);
      }
    );
  }

  public static class Config {}

  public String getUserServiceUrl() {
    return discoveryClient
      .getInstances("user-service")
      .stream()
      .findFirst()
      .map(serviceInstance -> serviceInstance.getUri().toString())
      .orElseThrow(() -> new RuntimeException("User service not available"));
  }

  public ServerWebExchange validateTokenAndAddHeader(
    ServerWebExchange exchange,
    String authHeader
  ) {
    jwtService.validateToken(authHeader);
    String userEmailId = jwtService.getEmailFromJwtToken(authHeader);
    UserResponse userResponse = template.getForObject(
      "http://localhost:9001/api/v1/users/email?email=" + userEmailId,
      UserResponse.class
    );
    ServerHttpRequest request = exchange
      .getRequest()
      .mutate()
      .header("User-Id", String.valueOf(userResponse.getId()))
      .build();
    return exchange.mutate().request(request).build();
  }
}
