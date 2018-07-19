package com.mailer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mailer.model.User;
/**
 * 
 * @author vishnu
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
