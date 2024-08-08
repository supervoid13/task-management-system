package com.uneev.task_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CommentCreationDto {

    @Schema(description = "Task id", example = "69")
    @Min(1)
    private Long taskId;

    @Schema(description = "Comment content", example = "good task! :)")
    private String content;
}
