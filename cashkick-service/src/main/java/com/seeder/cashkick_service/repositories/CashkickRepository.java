package com.seeder.cashkick_service.repositories;

import com.seeder.cashkick_service.entities.Cashkick;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashkickRepository extends JpaRepository<Cashkick,Long> {
}
