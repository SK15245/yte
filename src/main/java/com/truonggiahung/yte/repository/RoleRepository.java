package com.truonggiahung.yte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truonggiahung.yte.models.ERole;
import com.truonggiahung.yte.models.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {   
    // Lấy role theo enum
   Optional<Role> findByName(ERole name);
}
