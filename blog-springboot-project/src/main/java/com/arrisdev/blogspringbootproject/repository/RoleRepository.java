package com.arrisdev.blogspringbootproject.repository;

import com.arrisdev.blogspringbootproject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findById(Long id);
}
