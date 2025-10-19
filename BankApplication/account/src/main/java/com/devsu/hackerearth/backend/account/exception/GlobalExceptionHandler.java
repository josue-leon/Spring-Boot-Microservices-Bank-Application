package com.devsu.hackerearth.backend.account.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, HttpServletRequest request) {
        ErrorCode errorCode = ex.getErrorCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .error(errorCode.name())
                .message(errorCode.getMessage())
                .status(errorCode.getStatus().value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .details(ex.getDetails().isEmpty() ? null : ex.getDetails())
                .build();

        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException ex,
            HttpServletRequest request) {
        return handleBaseException(ex, request);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTransactionNotFoundException(TransactionNotFoundException ex,
            HttpServletRequest request) {
        return handleBaseException(ex, request);

    }

    @ExceptionHandler(InsufficienteAmountException.class)
    public ResponseEntity<ErrorResponse> InsufficienteAmountException(InsufficienteAmountException ex,
            HttpServletRequest request) {
        return handleBaseException(ex, request);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ErrorCode.VALIDATION_ERROR.getCode())
                .error("Validation Error")
                .message("Uno o mas campos tienen valores invalidos")
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .fieldErrrors(fieldErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex,
            HttpServletRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ErrorCode.ACCOUNT_INVALID_DATA.getCode())
                .error("Invalid Argument")
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ErrorCode.INTERNAL_ERROR.getCode())
                .error("Internal Error Server")
                .message("Error inesperado")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
