package com.devsu.hackerearth.backend.client.exception;

public class ClientDeletedException extends BaseException {

    public ClientDeletedException(Long id) {
        super(ErrorCode.CLIENT_DELETED, String.format("El Cliente con el ID '%d' ha sido eliminado.", id));
    }

    public ClientDeletedException(String dni) {
        super(
                ErrorCode.CLIENT_DELETED,
                String.format(
                        "El Cliente con DNI '%s' existe en el sistema pero ha sido eliminado. No se puede crear un nuevo cliente con el mismo DNI.",
                        dni));
    }

    public ClientDeletedException(String dni, String customMessage) {
        super(
                ErrorCode.CLIENT_DELETED,
                customMessage);
    }

}
