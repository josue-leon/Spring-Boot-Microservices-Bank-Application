package com.devsu.hackerearth.backend.account.exception;

import java.util.Map;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ErrorResponse {

    private EnumCode errorCode;
    private String error;
    private String message;
    private Integer status;
    private String path;
    private LocalDateTime timestamp;
    private Map<String, String> fieldErrrors;
    private Map<String, Object> details;

    public ErrorResponse(String error, String message, LocalDateTime timestamp) {
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ErrorResponse(EnumCode errorCode, String error, String message, Integer status, LocalDateTime timestamp) {
        this.errorCode = errorCode;
        this.error = error;
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }
}
