package com.uneev.task_management_system.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Dto class for responding info from server.
 */
@Data
public class ResponseInfoDto {
    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ResponseInfoDto(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
