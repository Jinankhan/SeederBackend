package com.seeder.cashkickservice.dto.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetContract {

  private  UUID id;
  private double perPayment;
  private double paymentAmount;
  private String termLength;
  private String type;
  private String name;
}
