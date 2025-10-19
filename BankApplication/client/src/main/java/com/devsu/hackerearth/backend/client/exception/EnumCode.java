package com.devsu.hackerearth.backend.client.exception;

public enum EnumCode {

    CLT_001("CLT-001"),
    CLT_002("CLT-002"),
    CLT_003("CLT-003"),
    CLT_004("CLT-004"),

    CLT_100("CLT-100"),
    CLT_101("CLT-101"),
    CLT_102("CLT-102"),

    GEN_999("GEN-999");

    private final String code;

    EnumCode(String code){
        this.code = code;
    }

    String keyCode(){
        return this.code;
    }

}
