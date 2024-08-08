package com.uneev.task_management_system.exceptions;


public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EmailAlreadyExistException(String msg) {
        super(msg);
    }
}
