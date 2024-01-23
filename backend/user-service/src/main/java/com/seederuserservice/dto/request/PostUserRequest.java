package com.seederuserservice.dto.request;

import com.seederuserservice.utils.Constants;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUserRequest {

  @NotNull(message = Constants.PASSWORD_ERROR)
  String password;

  @DecimalMin(value = "1.0", message = Constants.USER_CREDIT_ERROR)
  double creditAmount;

  @Length(message = Constants.NAME_ERROR, min = 4, max = 20)
  String name;

  @Email(message = Constants.EMAIL_ERROR)
  String email;
}
