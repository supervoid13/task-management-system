package com.uneev.task_management_system.controllers;

import com.uneev.task_management_system.dto.*;
import com.uneev.task_management_system.services.TaskService;
import com.uneev.task_management_system.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskControllerImpl implements TaskController {

    private final TaskService taskService;
    private final JwtTokenUtils jwtTokenUtils;


    @Override
    public List<TaskResponseDto> getTasksByCreatorIdOrPerformerId(
            @RequestParam(required = false) Long creatorId,
            @RequestParam(required = false) Long performerId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return taskService.getTaskResponseDtoListOnPageByCreatorIdAndPerformerId(
                creatorId,
                performerId,
                page,
                size
        );
    }

    @Override
    public TaskResponseDto getTaskById(@PathVariable Long id) {
        return taskService.getTaskResponseDtoById(id);
    }

    @Override
    public ResponseEntity<?> deleteTaskById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) {
        String email = jwtTokenUtils.getUsernameFromToken(jwt.substring(7));

        taskService.deleteById(id, email);

        return ResponseEntity.ok(
                new ResponseInfoDto(
                        HttpStatus.OK.value(),
                        "Task has been successfully deleted"
                )
        );
    }

    @Override
    public ResponseEntity<?> changeStatus(
            @RequestHeader("Authorization") String jwt,
            @RequestBody ChangeStatusDto changeStatusDto
    ) {
        String email = jwtTokenUtils.getUsernameFromToken(jwt.substring(7));

        taskService.changeStatus(changeStatusDto, email);

        return ResponseEntity.ok(new ResponseInfoDto(
                HttpStatus.OK.value(),
                "Task status has been successfully changed"
        ));
    }

    @Override
    public ResponseEntity<?> assignPerformer(
            @RequestHeader("Authorization") String jwt,
            @RequestBody AssignPerformerDto assignPerformerDto
    ) {
        String creatorEmail = jwtTokenUtils.getUsernameFromToken(jwt.substring(7));

        taskService.assignPerformer(assignPerformerDto, creatorEmail);

        return ResponseEntity.ok(new ResponseInfoDto(
                HttpStatus.OK.value(),
                "Task performer has been successfully assigned"
        ));
    }

    @Override
    public ResponseEntity<?> createTask(
            @RequestBody TaskCreationDto taskCreationDto,
            @RequestHeader("Authorization") String jwt
    ) {
        String email = jwtTokenUtils.getUsernameFromToken(jwt.substring(7));

        taskService.createTask(taskCreationDto, email);

        return new ResponseEntity<>(
                new ResponseInfoDto(
                        HttpStatus.CREATED.value(),
                        "Task successfully created"
                ),
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<?> editTask(
            @RequestBody TaskEditingDto taskEditingDto,
            @RequestHeader("Authorization") String jwt
    ) {
        String email = jwtTokenUtils.getUsernameFromToken(jwt.substring(7));

        taskService.editTask(taskEditingDto, email);

        return ResponseEntity.ok(new ResponseInfoDto(
                HttpStatus.OK.value(),
                "Task has been successfully edited"
        ));
    }

    @Override
    public ResponseEntity<?> addComment(
            @RequestBody CommentCreationDto commentCreationDto,
            @RequestHeader("Authorization") String jwt
    ) {
        String email = jwtTokenUtils.getUsernameFromToken(jwt.substring(7));

        taskService.addComment(commentCreationDto, email);

        return new ResponseEntity<>(
                new ResponseInfoDto(
                        HttpStatus.CREATED.value(),
                        "Comment has been successfully created"
                ),
                HttpStatus.CREATED
        );
    }
}
