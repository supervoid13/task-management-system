package com.uneev.task_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto class for user login.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User login form")
public class UserLoginDto {

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    @Schema(description = "User email", example = "ifyoureadthishaveaniceday@gmail.com")
    @NotNull
    private String email;

    @Schema(description = "User password", example = "qwerty123")
    @NotNull
    private String password;
}
