package com.seeder.cashkickservice.controller.cashkick;

import com.seeder.cashkickservice.dto.request.PostCashkick;
import com.seeder.cashkickservice.dto.response.GetCashkick;
import com.seeder.cashkickservice.service.cashkick.ICashkickService;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cashkicks")
public class CashkickController {

  private ICashkickService cashkickService;

  @Autowired
  CashkickController(ICashkickService cashkickService) {
    this.cashkickService = cashkickService;
  }

  @GetMapping
  public ResponseEntity<List<GetCashkick>> getUserCashkicks(
    @RequestParam UUID id,
    @RequestParam(defaultValue = "5", required = false) int pageSize,
    @RequestParam(defaultValue = "0", required = false) int pageNumber
  ) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(cashkickService.getUserCashkicks(id, pageSize, pageNumber));
  }

  @PostMapping
  public ResponseEntity<GetCashkick> postCashkick(
    @Valid @RequestBody PostCashkick postCashkick
  ) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(cashkickService.postCashkick(postCashkick));
  }
}
