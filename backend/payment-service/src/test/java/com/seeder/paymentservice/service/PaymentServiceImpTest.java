package com.seeder.paymentservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import com.seeder.paymentservice.dto.request.PostPayment;
import com.seeder.paymentservice.dto.response.GetPayment;
import com.seeder.paymentservice.dto.response.SavePayment;
import com.seeder.paymentservice.entity.Payment;
import com.seeder.paymentservice.exception.PaymentsNotFoundException;
import com.seeder.paymentservice.repository.PaymentRepository;
import com.seeder.paymentservice.utils.Converter;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PaymentServiceImpTest {

  @Mock
  private PaymentRepository paymentRepository;

  @Mock
  private Converter converter;

  @InjectMocks
  private PaymentServiceImp paymentService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void savePayment() {
    UUID userId = UUID.randomUUID();
    PostPayment postPayment = new PostPayment(180000, "Upcoming", userId);

    Payment mockedPayment = new Payment(
      UUID.randomUUID(),
      new Date(),
      180000,
      15000,
      "Upcoming",
      userId
    );
    SavePayment mockedSavePayment = new SavePayment(
      new Date(),
      15000,
      18000,
      "Upcoming"
    );

    when(converter.postPaymentToEntity(postPayment)).thenReturn(mockedPayment);
    when(paymentRepository.save(any())).thenReturn(mockedPayment);
    when(converter.entityToSavePayment(mockedPayment))
      .thenReturn(mockedSavePayment);

    SavePayment result = paymentService.savePayment(postPayment);

    assertEquals(
      mockedSavePayment.getExpectedAmount(),
      result.getExpectedAmount(),
      "Expected amount should match"
    );
    verify(converter, times(1)).postPaymentToEntity(postPayment);
    verify(paymentRepository, times(1)).save(any());
    verify(converter, times(1)).entityToSavePayment(mockedPayment);
  }

  @Test
  void getUsersLatestPaymentInstallments() {
    UUID userId = UUID.randomUUID();
    List<Payment> userPayments = List.of(
      createMockPayment(100.0, 50.0, new Date(), "PAID"),
      createMockPayment(80.0, 40.0, new Date(), "UNPAID")
    );
    when(paymentRepository.findByUserId(userId)).thenReturn(userPayments);
    List<GetPayment> result = paymentService.getUsersLatestPaymentInstallments(
      userId
    );

    assertEquals(
      12,
      result.size(),
      "The number of payment installments should be 12"
    );
  }

  @Test
  void getUsersLatestPaymentInstallmentsWithException() {
    UUID userId = UUID.randomUUID();

    when(paymentRepository.findByUserId(userId))
      .thenReturn(Collections.emptyList());

    PaymentsNotFoundException exception = assertThrows(
      PaymentsNotFoundException.class,
      () -> paymentService.getUsersLatestPaymentInstallments(userId)
    );

    assert (exception.getMessage().contains("Payments not found"));
  }

  @Test
  void generateMonthlyPaymentInstallments() {
    Payment payment = new Payment(
      UUID.randomUUID(),
      new Date(),
      4500,
      20000,
      "Upcoming",
      UUID.randomUUID()
    );
    List<GetPayment> result = paymentService.generateMonthlyPaymentInstallments(
      payment
    );
    assertEquals(
      12,
      result.size(),
      "The number of generated payment installments should be 12"
    );
  }

  private Payment createMockPayment(
    double outstandingAmount,
    double expectedAmount,
    Date dueDate,
    String status
  ) {
    Payment payment = new Payment();
    payment.setOutstandingAmount(outstandingAmount);
    payment.setExpectedAmount(expectedAmount);
    payment.setDueDate(dueDate);
    payment.setStatus(status);
    return payment;
  }
}
