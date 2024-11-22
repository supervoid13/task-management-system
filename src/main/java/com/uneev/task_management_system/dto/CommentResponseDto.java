package com.uneev.task_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Dto class for responding comment.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {

    @Schema(description = "Comment content", example = "damn tf you did")
    private String content;

    @Schema(description = "Date of creation")
    private LocalDateTime createdAt;

    private UserInfoDto user;
}
