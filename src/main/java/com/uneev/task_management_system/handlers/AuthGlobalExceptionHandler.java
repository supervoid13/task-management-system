package com.uneev.task_management_system.handlers;

import com.uneev.task_management_system.dto.ResponseInfoDto;
import com.uneev.task_management_system.exceptions.EmailAlreadyExistException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseInfoDto> handleBadCredentialsException(BadCredentialsException exception) {
        return new ResponseEntity<>(
                new ResponseInfoDto(HttpStatus.BAD_REQUEST.value(), "Invalid email or password"),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseInfoDto> handleExpiredJwtException(ExpiredJwtException exception) {

        return new ResponseEntity<>(
                new ResponseInfoDto(HttpStatus.UNAUTHORIZED.value(), "Token has been expired"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseInfoDto> handleSignatureException(SignatureException exception) {

        return new ResponseEntity<>(
                new ResponseInfoDto(HttpStatus.UNAUTHORIZED.value(), "Invalid token signature"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseInfoDto> handleUserNotFoundException(UsernameNotFoundException exception) {

        return new ResponseEntity<>(
                new ResponseInfoDto(HttpStatus.UNAUTHORIZED.value(), exception.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }


    @ExceptionHandler
    public ResponseEntity<ResponseInfoDto> handleEmailAlreadyExistException(
            EmailAlreadyExistException exception
    ) {

        return new ResponseEntity<>(
                new ResponseInfoDto(HttpStatus.BAD_REQUEST.value(),  exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
