package com.seeder.user_service.rapositories;

import com.seeder.user_service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
