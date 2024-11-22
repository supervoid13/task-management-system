package com.uneev.task_management_system.controllers;

import com.uneev.task_management_system.dto.AssignPerformerDto;
import com.uneev.task_management_system.dto.ChangeStatusDto;
import com.uneev.task_management_system.dto.CommentCreationDto;
import com.uneev.task_management_system.dto.TaskCreationDto;
import com.uneev.task_management_system.dto.TaskEditingDto;
import com.uneev.task_management_system.dto.TaskResponseDto;
import com.uneev.task_management_system.dto.ValidationErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/tasks")
@Tag(name = "Task Management")
@Validated
public interface TaskController {

    /**
     * Endpoint for retrieving page of tasks and filtering by {@code creatorId} and/or by {@code performerId}.
     * @param creatorId id of task creator.
     * @param performerId id of task performer.
     * @param page number of the required page.
     * @param size amount of elements on the required page.
     * @return page info and content with tasks.
     */
    @Operation(
            summary = "Retrieve a task list",
            description = "Endpoint for retrieving task list. Optional filters - by creatorId, by performerId.",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content
                    ),

            }
    )
    @GetMapping("")
    public Page<TaskResponseDto> getTasksByCreatorIdOrPerformerId(
            @RequestParam(required = false) @Min(1) Long creatorId,
            @RequestParam(required = false) @Min(1) Long performerId,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "5") @Min(1) int size
    );

    /**
     * Endpoint for retrieving task by id.
     * @param id id of required task.
     * @return task with specified id.
     */
    @Operation(
            summary = "Retrieve a task by id",
            description = "Endpoint for retrieving task by id.",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Not found - no task with such id",
                            responseCode = "404",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ValidationErrorResponseDto.class
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public TaskResponseDto getTaskById(@PathVariable @Min(1) Long id);

    /**
     * Endpoint for deleting task by id.
     * @param id id of required task.
     * @param jwt auth token of request.
     * @return info about deleting.
     */
    @Operation(
            summary = "Delete a task by id",
            description = "Endpoint for deleting task by id.",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Not found - no task with such id",
                            responseCode = "404",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Forbidden - you are not task creator",
                            responseCode = "403",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ValidationErrorResponseDto.class
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(
            @PathVariable @Min(1) Long id,
            @RequestHeader("Authorization") String jwt
    );

    /**
     * Endpoint for changing task status.
     * @param jwt auth token of request.
     * @param changeStatusDto task id and status.
     * @return info about changing.
     */
    @Operation(
            summary = "Change a task status",
            description = "Endpoint for changing task status.",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Not found - no task with such id",
                            responseCode = "404",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Forbidden - you are not task performer",
                            responseCode = "403",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ValidationErrorResponseDto.class
                                    )
                            )
                    )
            }
    )
    @PatchMapping("/status")
    public ResponseEntity<?> changeStatus(
            @RequestHeader("Authorization") String jwt,
            @RequestBody ChangeStatusDto changeStatusDto
    );

    /**
     * Endpoint for assigning performer for the task.
     * @param jwt auth token of request.
     * @param assignPerformerDto task id and performer data.
     * @return info about assigning performer.
     */
    @Operation(
            summary = "Assign a task performer",
            description = "Endpoint for assigning task performer.",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Not found - no task with such id",
                            responseCode = "404",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Forbidden - you are not task creator",
                            responseCode = "403",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ValidationErrorResponseDto.class
                                    )
                            )
                    )
            }
    )
    @PatchMapping("/performer")
    public ResponseEntity<?> assignPerformer(
            @RequestHeader("Authorization") String jwt,
            @RequestBody AssignPerformerDto assignPerformerDto
    );

    /**
     * Endpoint for creating new task.
     * @param taskCreationDto new task data.
     * @param jwt auth token of request.
     * @return info about creating task.
     */
    @Operation(
            summary = "Create a new task",
            description = "Endpoint for creating new task.",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ValidationErrorResponseDto.class
                                    )
                            )
                    ),
            }
    )
    @PostMapping("")
    public ResponseEntity<?> createTask(
            @RequestBody TaskCreationDto taskCreationDto,
            @RequestHeader("Authorization") String jwt
    );

    /**
     * Endpoint for editing task.
     * @param taskEditingDto edited task data.
     * @param jwt auth token of request.
     * @return info about editing task.
     */
    @Operation(
            summary = "Edit a task",
            description = "Endpoint for editing task.",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Not found - no task with such id",
                            responseCode = "404",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Forbidden - you are not task creator",
                            responseCode = "403",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ValidationErrorResponseDto.class
                                    )
                            )
                    )
            }
    )
    @PutMapping("")
    public ResponseEntity<?> editTask(
            @RequestBody TaskEditingDto taskEditingDto,
            @RequestHeader("Authorization") String jwt
    );

    /**
     * Endpoint for adding comment to a task.
     * @param commentCreationDto comment data.
     * @param jwt auth token of request.
     * @return info about adding comment.
     */
    @Operation(
            summary = "Add comment to a task",
            description = "Endpoint for adding comment to a task.",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "200",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Not found - no task with such id",
                            responseCode = "404",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ValidationErrorResponseDto.class
                                    )
                            )
                    )
            }
    )
    @PostMapping("/comments")
    public ResponseEntity<?> addComment(
            @RequestBody CommentCreationDto commentCreationDto,
            @RequestHeader("Authorization") String jwt
    );
}
