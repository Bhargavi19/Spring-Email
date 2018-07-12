package com.vishnu.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vishnu.project.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
