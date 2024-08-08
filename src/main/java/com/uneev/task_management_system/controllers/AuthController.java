package com.uneev.task_management_system.controllers;

import com.uneev.task_management_system.dto.JwtResponseDto;
import com.uneev.task_management_system.dto.UserLoginDto;
import com.uneev.task_management_system.dto.UserRegistrationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
@Validated
@Tag(name = "Authentication")
public interface AuthController {

    @Operation(
            summary = "Log in",
            description = "Endpoint for login. Returns JWT token if login is successful.",
            responses = {
                    @ApiResponse(
                            description = "OK - Logged in successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Bad Request - Invalid email/password",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody @Valid UserLoginDto userLoginDto);

    @Operation(
            summary = "Sign up",
            description = "Endpoint for registration. Returns JWT token if registration is successful.",
            responses = {
                    @ApiResponse(
                            description = "OK - Signed up successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Bad Request - Invalid or already taken email",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<JwtResponseDto> register(@RequestBody @Valid UserRegistrationDto userRegistrationDto);
}
