package com.uneev.task_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Dto class for responding about validation exceptions.
 */
@Data
@AllArgsConstructor
public class ValidationErrorResponseDto {
    private List<Violation> violations;
}