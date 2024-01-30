package com.seeder.paymentservice.service;

import com.seeder.paymentservice.dto.request.PostPayment;
import com.seeder.paymentservice.dto.response.GetPayment;
import com.seeder.paymentservice.dto.response.SavePayment;
import com.seeder.paymentservice.entity.Payment;
import com.seeder.paymentservice.exception.PaymentsNotFoundException;
import com.seeder.paymentservice.repository.PaymentRepository;
import com.seeder.paymentservice.utils.Constant;
import com.seeder.paymentservice.utils.Converter;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImp implements IPaymentService {

  private final PaymentRepository paymentRepository;
  private final Converter converter;

  @Autowired
  PaymentServiceImp(PaymentRepository paymentRepository, Converter converter) {
    this.paymentRepository = paymentRepository;
    this.converter = converter;
  }

  @Override
  public List<GetPayment> getUsersLatestPaymentInstallments(UUID id) {
    List<Payment> userPayments = paymentRepository.findByUserId(id);
    if (userPayments.isEmpty()) {
      throw new PaymentsNotFoundException("Payments not found");
    }

    Payment payment = userPayments.get(userPayments.size() - 1);

    return generateMonthlyPaymentInstallments(payment);
  }

  @Override
  public SavePayment savePayment(PostPayment postPayment) {
    Payment payment = converter.postPaymentToEntity(postPayment);
    payment.setExpectedAmount(
      Math.round(postPayment.getOutstandingAmount() / 12)
    );
    payment.setDueDate(new Date());
    return converter.entityToSavePayment(paymentRepository.save(payment));
  }

  public List<GetPayment> generateMonthlyPaymentInstallments(Payment payment) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(payment.getDueDate());
    List<GetPayment> payments = new ArrayList<>();
    double currentAmount = payment.getOutstandingAmount();

    for (int x = 0; x < 12; x++) {
      calendar.add(Calendar.MONTH, 1);
      currentAmount = Math.round(currentAmount - payment.getExpectedAmount());
      currentAmount = Math.max(currentAmount, 0);
      long daysDifference =
        (
          (calendar.getTimeInMillis() - new Date().getTime()) /
          (24 * 60 * 60 * 1000)
        );
      payments.add(
        new GetPayment(
          calendar.getTime(),
          payment.getExpectedAmount(),
          currentAmount,
          payment.getStatus(),
          daysDifference + Constant.DAYS_REMAINING_MESSAGE
        )
      );
    }

    return payments;
  }
}
