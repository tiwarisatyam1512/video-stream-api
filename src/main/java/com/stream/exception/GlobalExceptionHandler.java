package com.stream.exception;

import com.stream.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Global exception handler for handling all application-wide exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles all ResponseStatusException and returns structured error responses.
     *
     * @param ex The thrown {@link ResponseStatusException}.
     * @return A structured error response.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleResponseStatusException(ResponseStatusException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                UUID.randomUUID().toString(), // Unique error ID
                ex.getStatusCode().value(),
                ex.getReason(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    /**
     * Handles generic exceptions (e.g., NullPointerException, IllegalArgumentException).
     *
     * @param ex The thrown {@link Exception}.
     * @return A structured error response.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                UUID.randomUUID().toString(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}