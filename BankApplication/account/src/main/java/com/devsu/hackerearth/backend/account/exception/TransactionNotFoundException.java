package com.devsu.hackerearth.backend.account.exception;

public class TransactionNotFoundException extends BaseException {

    public TransactionNotFoundException(Long id) {
        super(ErrorCode.TRANSACTION_NOT_FOUND, "Transaccion no encontrada id: " + id);
        this.addDetail("transactionId", id);
    }
}
