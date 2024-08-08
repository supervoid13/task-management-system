package com.uneev.task_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class TaskEditingDto {

    @Schema(description = "Task id", example = "69")
    @Min(1)
    private Long id;

    @Schema(description = "New task title", example = "This is new task title")
    private String title;

    @Schema(description = "Task description", example = "This is new task description")
    private String description;
}
