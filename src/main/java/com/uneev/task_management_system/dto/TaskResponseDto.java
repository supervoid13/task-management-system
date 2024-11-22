package com.uneev.task_management_system.dto;

import com.uneev.task_management_system.enums.Priority;
import com.uneev.task_management_system.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Dto class for responding task.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDto {

    @Schema(description = "Task id", example = "69")
    private Long id;

    @Schema(description = "Task title", example = "Fix Task Controller")
    private String title;

    @Schema(description = "Task description", example = "Task Controller doesn't work. Please fix it.")
    private String description;

    @Schema(description = "Task status", example = "PROCESSING")
    private Status status;

    @Schema(description = "Task priority", example = "HIGH")
    private Priority priority;

    private UserInfoDto creator;

    private UserInfoDto performer;

    private List<CommentResponseDto> comments;
}
