package com.uneev.task_management_system.services;

import com.uneev.task_management_system.dto.UserRegistrationDto;
import com.uneev.task_management_system.exceptions.EmailAlreadyExistException;
import com.uneev.task_management_system.utils.JwtTokenUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * Service for the actions related to user auth.
 */
@Service
@RequiredArgsConstructor
@Validated
public class AuthService {

    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public String createToken(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                email,
                password
        ));

        UserDetails userDetails = userService.loadUserByUsername(email);

        return jwtTokenUtils.generateToken(userDetails);
    }

    @Transactional
    public void createUser(@Valid UserRegistrationDto userRegistrationDto) {
        if (userService.checkUserExist(userRegistrationDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistException(
                    String.format("Email '%s' is already taken", userRegistrationDto.getEmail())
            );
        }

        userService.createUser(userRegistrationDto);
    }

    @Transactional
    public String createUserAndReturnJwtToken(UserRegistrationDto userRegistrationDto) {
        createUser(userRegistrationDto);

        return createToken(userRegistrationDto.getEmail(), userRegistrationDto.getPassword());
    }
}
