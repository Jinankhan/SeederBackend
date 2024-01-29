package com.seeder.cashkickservice.dto.request;

import com.seeder.cashkickservice.utils.Constants;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostContract {

  @DecimalMin(value = "1", message = Constants.PER_PAYMENT_ERROR)
 private  double perPayment;

  @DecimalMin(value = "1", message = Constants.PAYMENT_AMOUNT_ERROR)
  private double paymentAmount;

  @DecimalMin(value = "1", message = Constants.TERM_LENGTH_ERROR)
  private double termLength;

  @NotBlank
  @Length(min = 1, max = 10, message = Constants.TYPE_ERROR)
  private String type;

  @NotBlank
  @Length(min = 1, max = 10, message = Constants.NAME_ERROR)
  private String name;
}
