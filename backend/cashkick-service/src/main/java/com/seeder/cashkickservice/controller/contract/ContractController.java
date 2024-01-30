package com.seeder.cashkickservice.controller.contract;

import com.seeder.cashkickservice.dto.request.PostContract;
import com.seeder.cashkickservice.dto.response.GetContract;
import com.seeder.cashkickservice.service.contract.ContractServiceImp;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contracts")
public class ContractController {

  private ContractServiceImp contractService;

  ContractController(ContractServiceImp contractService) {
    this.contractService = contractService;
  }

  @PostMapping
  public ResponseEntity<GetContract> saveContract(
    @Valid @RequestBody PostContract postContract
  ) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(contractService.saveContract(postContract));
  }

  @GetMapping
  public ResponseEntity<List<GetContract>> getAllContracts(
    @RequestParam(defaultValue = "5", required = false) int pageSize,
    @RequestParam(defaultValue = "0", required = false) int pageNumber
  ) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(contractService.getAllContracts(pageSize, pageNumber));
  }

  @GetMapping("/{id}/user-contracts")
  public ResponseEntity<List<GetContract>> getUserContracts(
    @PathVariable UUID id
  ) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(contractService.getUserContracts(id));
  }
}
