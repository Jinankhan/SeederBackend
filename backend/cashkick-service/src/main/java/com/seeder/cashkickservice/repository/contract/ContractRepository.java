package com.seeder.cashkickservice.repository.contract;

import com.seeder.cashkickservice.entity.Contract;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {
  List<Contract> findByIdIn(List<UUID> uuids);
}
