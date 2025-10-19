package com.devsu.hackerearth.backend.client.exception;

public class ClientNotFoundException extends BaseException {
    public ClientNotFoundException(Long id) {
        super(ErrorCode.CLIENT_NOT_FOUND, "Cliente no encontrado, id: " + id);
        this.addDetail("clientId", id);
    }

    public ClientNotFoundException(String dni) {
        super(ErrorCode.CLIENT_NOT_FOUND, "Cliente no encontrado, DNI: " + dni);
        this.addDetail("dni", dni);
    }

}
