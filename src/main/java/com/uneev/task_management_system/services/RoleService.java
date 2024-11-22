package com.uneev.task_management_system.services;

import com.uneev.task_management_system.entities.Role;
import com.uneev.task_management_system.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for the actions related to roles.
 */
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    Optional<Role> getUserRole() {
        return roleRepository.findByName("ROLE_USER");
    }
}
