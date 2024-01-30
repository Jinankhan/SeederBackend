package com.seeder.paymentservice.service;

import com.seeder.paymentservice.dto.request.PostPayment;
import com.seeder.paymentservice.dto.response.GetPayment;
import com.seeder.paymentservice.dto.response.SavePayment;
import java.util.List;
import java.util.UUID;

public interface IPaymentService {
  List<GetPayment> getUsersLatestPaymentInstallments(UUID id);
  SavePayment savePayment(PostPayment postPayment);
}
