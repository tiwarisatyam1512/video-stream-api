package com.stream.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

/**
 * DTO class for returning structured error responses.
 */
@Getter
@AllArgsConstructor
public class ErrorResponseDTO {
    private String errorId;
    private int statusCode;
    private String message;
    private LocalDateTime timestamp;
}
