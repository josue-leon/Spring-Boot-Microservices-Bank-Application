package com.devsu.hackerearth.backend.account.exception;

public class AccountNotFoundException extends BaseException {

    public AccountNotFoundException(Long id) {
        super(ErrorCode.ACCOUNT_NOT_FOUND, "Cuenta no encontrada, id: " + id);
        this.addDetail("accountId", id);
    }

    public AccountNotFoundException(String accountNumber) {
        super(ErrorCode.ACCOUNT_NOT_FOUND, "Cuenta no encontrada, numero: " + accountNumber);
        this.addDetail("accountNumber", accountNumber);
    }

}
