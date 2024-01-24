package com.seeder.apigateway.filter;

import java.util.List;
import java.util.function.Predicate;
import com.seeder.apigateway.utils.Constants;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

  public static final List<String> openApiEndpoints = Constants.OPEN_ENDPOINTS_LIST;

  public Predicate<ServerHttpRequest> isSecured = request ->
    openApiEndpoints
      .stream()
      .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
