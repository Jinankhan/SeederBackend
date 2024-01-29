package com.seeder.paymentservice.dto.response;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPayment {

 private Date dueDate;
 private double expectedAmount;
 private double outstandingAmount;
 private String status;
 private String upcomingPayment;
}
