package com.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.email.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
