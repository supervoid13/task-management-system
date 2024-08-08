package com.uneev.task_management_system.exceptions;

public class PerformerNotFoundException extends RuntimeException {
    public PerformerNotFoundException(String message) {
        super(message);
    }
}
