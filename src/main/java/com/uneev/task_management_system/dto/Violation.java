package com.uneev.task_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class for representation violations during validation.
 */
@Data
@AllArgsConstructor
public class Violation {
    private String fieldName;
    private String message;
}