package com.devsu.hackerearth.backend.account.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // Errores Cuenta
    ACCOUNT_NOT_FOUND(EnumCode.ACC_001, "Cuenta no encontrada", HttpStatus.NOT_FOUND),
    ACCOUNT_EXISTS(EnumCode.ACC_002, "Ya existe una cuenta con ese numero", HttpStatus.CONFLICT),
    ACCOUNT_INVALID_DATA(EnumCode.ACC_003, "Datos de la cuenta no validos", HttpStatus.BAD_REQUEST),
    ACCOUNT_INACTIVE(EnumCode.ACC_004, "La cuenta esta inactiva", HttpStatus.FORBIDDEN),

    // Errores Transacción
    TRANSACTION_NOT_FOUND(EnumCode.TRX_100, "Transaccion no encontrada", HttpStatus.NOT_FOUND),
    INSUFFICIENT_AMOUNT(EnumCode.TRX_101, "Saldo no disponible", HttpStatus.BAD_REQUEST),
    INVALID_TRASACTION(EnumCode.TRX_102, "Monto de transaccion invalida", HttpStatus.BAD_REQUEST),
    TRANSACTION_ALREADY_PROCESSED(EnumCode.TRX_103, "Transaccion ya procesada", HttpStatus.CONFLICT),

    // Errores Validación
    VALIDATION_ERROR(EnumCode.VAL_200, "Error de validacion de datos", HttpStatus.BAD_REQUEST),
    INVALID_ACCOUNT_NUMBER(EnumCode.VAL_201, "Numero de cuenta invalido", HttpStatus.BAD_REQUEST),
    INVALID_AMMOUNT(EnumCode.VAL_202, "Monto Invalido", HttpStatus.BAD_REQUEST),
    INVALID_DATE_RANGE(EnumCode.VAL_203, "Rango de Fechas Invalido", HttpStatus.BAD_REQUEST),

    // Errores Capa Cliente
    CLIENT_NOT_FOUND_EXTERNAL(EnumCode.CLT_050, "Cliente no encontrado en el servicio extenro", HttpStatus.NOT_FOUND),
    CLIENT_SERVICE_UNAVAILABLE(EnumCode.CLT_051, "Servicio de Cliente no disponible", HttpStatus.SERVICE_UNAVAILABLE),

    // Errores Generales
    INTERNAL_ERROR(EnumCode.GEN_999, "Error intenro del servidor", HttpStatus.INTERNAL_SERVER_ERROR);

    private final EnumCode code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(EnumCode code, String message, HttpStatus status) {
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
