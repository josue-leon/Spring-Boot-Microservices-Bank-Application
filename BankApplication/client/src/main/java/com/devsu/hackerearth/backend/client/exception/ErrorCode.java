package com.devsu.hackerearth.backend.client.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    CLIENT_NOT_FOUND(EnumCode.CLT_001, "Cliente no encontrado", HttpStatus.NOT_FOUND),
    CLIENT_ALREADY_EXISTS(EnumCode.CLT_002, "Ya existe un Cliente con ese DNI", HttpStatus.CONFLICT),
    CLIENT_INVALID_DATA(EnumCode.CLT_003, "Datos del Cliente invalidos", HttpStatus.BAD_REQUEST),
    CLIENT_DELETED(EnumCode.CLT_004, "El cliente existe pero ha sido eliminado", HttpStatus.CONFLICT),

    VALIDATION_ERROR(EnumCode.CLT_100, "Error de validacion", HttpStatus.BAD_REQUEST),
    INVALID_DNI(EnumCode.CLT_101, "DNI invalido", HttpStatus.BAD_REQUEST),
    INVALID_AGE(EnumCode.CLT_102, "Edad invalida", HttpStatus.BAD_REQUEST),

    INTERNAL_ERROR(EnumCode.GEN_999, "Error interno en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);

    private final EnumCode code;
    private final String message;
    private final HttpStatus status;

    private ErrorCode(EnumCode code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public EnumCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
