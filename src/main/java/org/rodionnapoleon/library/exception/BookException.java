package org.rodionnapoleon.library.exception;

import org.springframework.http.HttpStatus;

public record BookException(String message, Throwable throwable, HttpStatus httpStatus) {}
