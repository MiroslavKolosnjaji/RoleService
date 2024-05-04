package com.example.roleservice.repository;

import com.example.roleservice.model.Role;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * @author Miroslav Kološnjaji
 */
public interface RoleRepository extends ReactiveCrudRepository<Role, Long> {
}
