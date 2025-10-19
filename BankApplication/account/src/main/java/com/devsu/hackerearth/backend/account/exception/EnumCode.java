package com.devsu.hackerearth.backend.account.exception;

public enum EnumCode {

    // Códigos Cuenta
    ACC_001("ACC-001"),
    ACC_002("ACC-002"),
    ACC_003("ACC-003"),
    ACC_004("ACC-004"),

    // Códigos Transacción
    TRX_100("TRX-100"),
    TRX_101("TRX-101"),
    TRX_102("TRX-102"),
    TRX_103("TRX-103"),

    // Códigos Validación
    VAL_200("VAL-200"),
    VAL_201("VAL-201"),
    VAL_202("VAL-202"),
    VAL_203("VAL-203"),

    // Códigos Capa Cliente
    CLT_050("CLT-050"),
    CLT_051("CLT-051"),

    // Código General
    GEN_999("GEN-999");

    private final String code;

    EnumCode(String code) {
        this.code = code;
    }

    String keyCode() {
        return this.code;
    }

}
