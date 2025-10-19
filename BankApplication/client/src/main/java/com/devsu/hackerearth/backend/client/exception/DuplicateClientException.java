package com.devsu.hackerearth.backend.client.exception;

public class DuplicateClientException extends BaseException {

    public DuplicateClientException(String dni) {
        super(ErrorCode.CLIENT_ALREADY_EXISTS, "Ya existe un Cliente registrado con ese dni: " + dni);
        this.addDetail("dni", dni);
        this.addDetail("nota", "utilice otro DNI");
    }
}
