package com.uneev.task_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreationDto {

    @Schema(description = "Task title", example = "Fix db connection")
    private String title;

    @Schema(description = "Task description", example = "You need to immediately fix db connection")
    private String description;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    @Schema(description = "Performer email", example = "optionalperformer@gmail.com")
    private String performerEmail;
}
