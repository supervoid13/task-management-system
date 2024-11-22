package com.uneev.task_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Dto class for assigning performer for the task.
 */
@Data
public class AssignPerformerDto {

    @Schema(description = "Task id", example = "69")
    @Min(1)
    private Long taskId;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    @Schema(description = "Performer email", example = "bestperformer@gmail.com")
    private String performerEmail;
}
