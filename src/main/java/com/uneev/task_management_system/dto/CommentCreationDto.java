package com.uneev.task_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Dto class for adding comment for the task.
 */
@Data
public class CommentCreationDto {

    @Schema(description = "Task id", example = "69")
    @Min(1)
    @NotNull
    private Long taskId;

    @Schema(description = "Comment content", example = "good task! :)")
    @NotNull
    private String content;
}
