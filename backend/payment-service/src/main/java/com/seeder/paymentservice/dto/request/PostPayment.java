package com.seeder.paymentservice.dto.request;

import com.seeder.paymentservice.utils.Constant;
import java.util.UUID;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostPayment {

  @DecimalMin(value = "1", message = Constant.OUTSTANDING_AMOUNT_ERROR)
 private  double outstandingAmount;

  @NotBlank
  @Length(message = Constant.STATUS_ERROR, min = 6, max = 10)
private   String status;

  @NotNull
  private UUID userId;
}
