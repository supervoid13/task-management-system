package com.uneev.task_management_system.services;

import com.uneev.task_management_system.dto.UserRegistrationDto;
import com.uneev.task_management_system.entities.User;
import com.uneev.task_management_system.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * Service for actions related to users and their details.
 */
@Service
@RequiredArgsConstructor
@Validated
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    /**
     * Retrieving user by email.
     * @param email user email.
     * @return user with specified email.
     */
    public User getByEmail(String email) {
        return checkUserExist(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User '%s' not found", email))
        );
    }

    /**
     * Method for retrieving possibly existing user.
     * @param email user email.
     * @return {@link Optional} of user.
     */
    public Optional<User> checkUserExist(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getByEmail(username);

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .toList()
        );
    }

    /**
     * Creating new user.
     * @param userRegistrationDto new user data.
     */
    @Transactional
    public void createUser(@Valid UserRegistrationDto userRegistrationDto) {
        User user = modelMapper.map(userRegistrationDto, User.class);

        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setRoles(List.of(roleService.getUserRole().orElseThrow()));

        userRepository.save(user);
    }
}
