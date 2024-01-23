package com.seeder.apigateway.payload.request;

import javax.validation.constraints.Email;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AuthenticationRequest {

  @Email(message = "invalid email")
  String email;
}
