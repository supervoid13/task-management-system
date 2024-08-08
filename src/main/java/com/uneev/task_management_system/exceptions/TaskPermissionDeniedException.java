package com.uneev.task_management_system.exceptions;

public class TaskPermissionDeniedException extends RuntimeException {
    public TaskPermissionDeniedException(String message) {
        super(message);
    }
}
