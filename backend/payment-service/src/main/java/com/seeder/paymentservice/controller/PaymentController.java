package com.seeder.paymentservice.controller;

import com.seeder.paymentservice.dto.request.PostPayment;
import com.seeder.paymentservice.dto.response.GetPayment;
import com.seeder.paymentservice.dto.response.SavePayment;
import com.seeder.paymentservice.service.IPaymentService;
import com.seeder.paymentservice.utils.Constant;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.API_ENDPOINT)
public class PaymentController {
  private final IPaymentService paymentService;

  PaymentController(IPaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping
  public ResponseEntity<List<GetPayment>> getAllPayments(
    @RequestParam UUID id
  ) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(paymentService.getUsersLatestPaymentInstallments(id));
  }

  @PostMapping
  public ResponseEntity<SavePayment> savePayment(
    @Valid @RequestBody PostPayment postPayment
  ) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(paymentService.savePayment(postPayment));
  }
}
