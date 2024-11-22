package com.uneev.task_management_system.handlers;

import com.uneev.task_management_system.dto.ResponseInfoDto;
import com.uneev.task_management_system.exceptions.PerformerNotFoundException;
import com.uneev.task_management_system.exceptions.TaskNotFoundException;
import com.uneev.task_management_system.exceptions.TaskPermissionDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global handler for exceptions related to task management.
 */
@RestControllerAdvice
public class TaskGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseInfoDto> handlePerformerNotFoundException(
            PerformerNotFoundException exception
    ) {
        return new ResponseEntity<>(
                new ResponseInfoDto(HttpStatus.BAD_REQUEST.value(), exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseInfoDto> handleTaskNotFoundException(
            TaskNotFoundException exception
    ) {
        return new ResponseEntity<>(
                new ResponseInfoDto(HttpStatus.NOT_FOUND.value(), exception.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseInfoDto> handleTaskPermissionDeniedException(
            TaskPermissionDeniedException exception
    ) {
        return new ResponseEntity<>(
                new ResponseInfoDto(HttpStatus.FORBIDDEN.value(), exception.getMessage()),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseInfoDto> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception
    ) {
        return new ResponseEntity<>(
                new ResponseInfoDto(HttpStatus.BAD_REQUEST.value(), exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
