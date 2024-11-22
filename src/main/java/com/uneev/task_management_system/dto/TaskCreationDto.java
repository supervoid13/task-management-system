package com.uneev.task_management_system.dto;

import com.uneev.task_management_system.enums.Priority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto class for creating new task.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreationDto {

    @Schema(description = "Task title", example = "Fix db connection")
    @NotNull
    private String title;

    @Schema(description = "Task description", example = "You need to immediately fix db connection")
    @NotNull
    private String description;

    @Schema(description = "Task priority", example = "HIGH")
    @NotNull
    private Priority priority;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    @Schema(description = "Performer email", example = "optionalperformer@gmail.com")
    private String performerEmail;
}
