package com.uneev.task_management_system.controllers;

import com.uneev.task_management_system.dto.JwtResponseDto;
import com.uneev.task_management_system.dto.UserLoginDto;
import com.uneev.task_management_system.dto.UserRegistrationDto;
import com.uneev.task_management_system.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;


    @Override
    public ResponseEntity<JwtResponseDto> login(@RequestBody @Valid UserLoginDto userLoginDto) {
        String token = authService.createToken(userLoginDto.getEmail(), userLoginDto.getPassword());

        return ResponseEntity.ok(new JwtResponseDto(token));
    }

    @Override
    public ResponseEntity<JwtResponseDto> register(@RequestBody UserRegistrationDto userRegistrationDto) {
        String token = authService.createUserAndReturnJwtToken(userRegistrationDto);

        return new ResponseEntity<>(
                new JwtResponseDto(token),
                HttpStatus.CREATED
        );
    }
}
