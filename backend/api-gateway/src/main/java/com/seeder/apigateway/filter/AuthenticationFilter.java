package com.seeder.apigateway.filter;

import com.seeder.apigateway.exception.AccessDeniedException;
import com.seeder.apigateway.payload.response.UserResponse;
import com.seeder.apigateway.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

@Component
public class AuthenticationFilter
  extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

  @Autowired
  private RouteValidator validator;

  @Autowired
  private RestTemplate template;


  @Autowired
  private JwtService jwtService;

  public AuthenticationFilter() {
    super(Config.class);
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
            ServerWebExchange newExchange = validateTokenAndAddHeader(
              exchange,
              authHeader
            );
            return chain.filter(newExchange);
          } catch (Exception e) {
            throw new AccessDeniedException(
              "un authorized access to application"
            );
          }
        }
        return chain.filter(exchange);
      }
    );
  }

  public static class Config {}

  public ServerWebExchange validateTokenAndAddHeader(
    ServerWebExchange exchange,
    String authHeader
  ) {
    jwtService.validateToken(authHeader);
    String userEmailId = jwtService.getEmailFromJwtToken(authHeader);
    UserResponse userResponse = template.getForObject(
      " http://localhost:9001/api/v1/users/email?email=" + userEmailId,
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
