package com.uneev.task_management_system.dto;

import com.uneev.task_management_system.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * Dto class for changing status for the task.
 */
@Data
public class ChangeStatusDto {

    @Schema(description = "Task id", example = "69")
    @Min(1)
    private Long taskId;

    @Schema(description = "New task status", example = "PROCESSING")
    private Status status;
}
