package com.seeder.contract_service.repositories;

import com.seeder.contract_service.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract,Long> {
}
