package com.vishnu.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vishnu.project.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
