package com.unpontdev.comparator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.unpontdev.comparator.entities.Role;

/**
 * Extending JPA repo, this interface
 * communicates with DB.
 */
public interface RoleRepository extends JpaRepository<Role, String> {}
