package com.quantus.backend.utils;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-08-02
 *
 * This exception class extends RunTimeException and handles errors based on HTTP Status Codes.
 */

public class CustomExceptionHandler extends RuntimeException {

    //Default case
    public CustomExceptionHandler(String message) {
        super(message);
    }

    public static class BadRequestCustomException extends CustomExceptionHandler {
        public BadRequestCustomException(String msg) {
            super(msg);
        }
    }

    public static class NotFoundCustomException extends CustomExceptionHandler {
        public NotFoundCustomException(String msg) {
            super(msg);
        }
    }

    public static class UnauthorizedCustomException extends CustomExceptionHandler {
        public UnauthorizedCustomException(String msg) {
            super(msg);
        }
    }
}
