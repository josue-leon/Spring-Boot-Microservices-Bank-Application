package com.devsu.hackerearth.backend.account.exception;

public class InsufficienteAmountException extends BaseException {

    public InsufficienteAmountException(Double currentBalance, Double requestedAmount) {
        super(
                ErrorCode.INSUFFICIENT_AMOUNT,
                String.format("Saldo no disponible. Saldo Actual: %.2f , Solicitado: %.2f", currentBalance,
                        requestedAmount));

        this.addDetail("currentBalance", currentBalance);
        this.addDetail("requestedAmount", requestedAmount);
        this.addDetail("deficit", requestedAmount - currentBalance);
        this.addDetail("suggestion", "Realice un desposito antes de intentar el retiro");

    }
}
