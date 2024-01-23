package com.seeder.apigateway.payload.response;

import java.util.UUID;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserResponse {

  UUID id;
  String email;
}
