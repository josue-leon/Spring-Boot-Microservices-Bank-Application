package com.devsu.hackerearth.backend.account.model;

import javax.validation.ValidationException;

public enum AccountType {
    AHORRO("AHORRO"),
    CORRIENTE("CORRIENTE");

    private final String value;

    AccountType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValid(String type) {
        if (type == null)
            return false;

        for (AccountType accountType : values()) {
            if (accountType.getValue().equalsIgnoreCase(type) || accountType.name().equalsIgnoreCase(type))
                return true;
        }

        return false;
    }

    public static AccountType fromString(String type) {
        if (type == null)
            throw new IllegalArgumentException("El tipo de cuenta no puede ser nulo");

        for (AccountType accountType : values()) {
            if (accountType.getValue().equalsIgnoreCase(type) || accountType.name().equalsIgnoreCase(type))
                return accountType;
        }

        throw new ValidationException(
                String.format(
                        "Tipo de cuenta no válido: %s. Valores permitidos: %s",
                        type, values().toString()));
    }
}
