package com.seeder.cashkickservice.repository.cashkick;

import com.seeder.cashkickservice.entity.Cashkick;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashkickRepository extends JpaRepository<Cashkick, UUID> {}
