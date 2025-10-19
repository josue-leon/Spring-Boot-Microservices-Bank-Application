package com.devsu.hackerearth.backend.account.model;

public enum TransactionType {
    DEPOSITO("DEPOSITO"),
    RETIRO("RETIRO");

    private final String value;

    TransactionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValid(String type) {
        if (type == null)
            return false;

        for (TransactionType transactionType : values()) {
            if (transactionType.getValue().equals(type))
                return true;
        }

        return false;
    }

    public static TransactionType fromString(String type) {
        if(type == null) {
            throw new IllegalArgumentException("El tipo de transacción no puede ser nulo");
        }

        for(TransactionType transactionType : values()) {
            if(transactionType.getValue().equals(type)) {
                return transactionType;
            }
        }

        throw new IllegalArgumentException(String.format("Tipo de transacción inválido: %s. Valores permitidos: DEPOSITO, RETIRO", type));
    }

}
