package com.unpontdev.comparator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.unpontdev.comparator.entities.Role;


public interface RoleRepository extends JpaRepository<Role, String> {

}
