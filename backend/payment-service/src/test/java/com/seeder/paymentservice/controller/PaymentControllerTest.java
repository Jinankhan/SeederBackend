package com.seeder.paymentservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.seeder.paymentservice.dto.request.PostPayment;
import com.seeder.paymentservice.dto.response.GetPayment;
import com.seeder.paymentservice.dto.response.SavePayment;
import com.seeder.paymentservice.service.IPaymentService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class PaymentControllerTest {

  @Mock
  private IPaymentService paymentService;

  @InjectMocks
  private PaymentController paymentController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllPayments() {
    UUID userId = UUID.randomUUID();
    List<GetPayment> payments = new ArrayList<>();

    when(paymentService.getUsersLatestPaymentInstallments(userId))
      .thenReturn(payments);

    ResponseEntity<List<GetPayment>> responseEntity = paymentController.getAllPayments(
      userId
    );

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(payments, responseEntity.getBody());

    verify(paymentService, times(1)).getUsersLatestPaymentInstallments(userId);
  }

  @Test
  void testSavePayment() {
    PostPayment postPayment = new PostPayment();
    SavePayment savePayment = new SavePayment();

    when(paymentService.savePayment(postPayment)).thenReturn(savePayment);

    ResponseEntity<SavePayment> responseEntity = paymentController.savePayment(
      postPayment
    );

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(savePayment, responseEntity.getBody());

    verify(paymentService, times(1)).savePayment(postPayment);
  }
}
