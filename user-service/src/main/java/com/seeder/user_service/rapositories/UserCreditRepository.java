package com.seeder.user_service.rapositories;

import com.seeder.user_service.entities.UserCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCreditRepository extends JpaRepository<UserCredit,Long> {
    // Find a UserCredit by user ID (or other identifiers, like username, etc.)
}
