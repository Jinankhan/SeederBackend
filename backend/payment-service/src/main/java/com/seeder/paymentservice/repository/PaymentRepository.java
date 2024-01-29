package com.seeder.paymentservice.repository;

import com.seeder.paymentservice.entity.Payment;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
  List<Payment> findByUserId(UUID id);
}
