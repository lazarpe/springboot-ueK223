package com.example.demo.domain.exception;

import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.OperationsException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiExceptionHandler {

    private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private static final HttpStatus FORBIDDEN = HttpStatus.FORBIDDEN;
    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    private static final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;
    private static final HttpStatus CONFLICT = HttpStatus.CONFLICT;

    @ExceptionHandler(value = {NoSuchElementException.class, InstanceNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, NOT_FOUND);
    }

    @ExceptionHandler(value = {NullPointerException.class, ArithmeticException.class, ClassCastException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        ApiException apiException = new ApiException(
                "Something went wrong on our side...  Try the action again.",
                INTERNAL_SERVER_ERROR,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {PermissionDeniedDataAccessException.class})
    public ResponseEntity<Object> handleForbiddenException(NonTransientDataAccessException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                FORBIDDEN,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, FORBIDDEN);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> handleUnauthorizedException(RuntimeException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                UNAUTHORIZED,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, UNAUTHORIZED);
    }

    @ExceptionHandler(value = {InstanceAlreadyExistsException.class})
    public ResponseEntity<Object> handleConflictException(OperationsException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                CONFLICT,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, CONFLICT);
    }

    @ExceptionHandler(value = {RequestRejectedException.class})
    public ResponseEntity<Object> handleBadRequestException(RuntimeException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, BAD_REQUEST);
    }

}
