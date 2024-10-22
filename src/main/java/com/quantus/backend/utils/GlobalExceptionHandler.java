package com.quantus.backend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-16
 *
 * In order to throw accurate errors with Spring Security and not get the default 403 for every error,
 * It is necessary to throw a custom exception (CustomExceptionHandler), then pass it through to a
 * GlobalExceptionHandler that will activate a unique HTTP error code depending on the request.
 *
 * The default return HTTP code is 200 on success (sent by any controller. The default return HTTP
 * code on an error is 400, thrown as an exception in the service layer, and transformed into a ResponseEntity
 * by this class.
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    //Default case
    @ExceptionHandler(CustomExceptionHandler.class)
    public ResponseEntity<Object> handleDefaultRequestException(CustomExceptionHandler ex) {
        return new CustomResponseEntityHandler(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
    }

    @ExceptionHandler(CustomExceptionHandler.BadRequestCustomException.class)
    public ResponseEntity<Object> handleBadRequestException(CustomExceptionHandler ex) {
        return new CustomResponseEntityHandler(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(CustomExceptionHandler.NotFoundCustomException.class)
    public ResponseEntity<Object> handleNotFoundRequestException(CustomExceptionHandler ex) {
        return new CustomResponseEntityHandler(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(CustomExceptionHandler.UnauthorizedCustomException.class)
    public ResponseEntity<Object> handleUnauthorizedRequestException(CustomExceptionHandler ex) {
        return new CustomResponseEntityHandler(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }
}
