package com.seeder.cashkick_service.repositories;

import com.seeder.cashkick_service.entities.Cashkick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashkickRepository extends JpaRepository<Cashkick,Long> {
}
