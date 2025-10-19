package com.devsu.hackerearth.backend.account.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.devsu.hackerearth.backend.account.exception.InsufficienteAmountException;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts", indexes = {
        @Index(name = "idx_account_number", columnList = "number", unique = true),
        @Index(name = "idx_account_client_id", columnList = "client_id"),
        @Index(name = "idx_account_deleted", columnList = "deleted")
})
public class Account extends Base {
    @Column(name = "number", nullable = false, unique = true, length = 20)
    @NotBlank(message = "Número de Cuenta obligatorio")
    private String number;

    @Column(name = "type", nullable = false, length = 9)
    @NotBlank(message = "Tipo de cuenta obligatorio")
    private String type;

    @Column(name = "initial_amount", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Monto inicial obligatorio")
    @Min(value = 0, message = "EL monto inicial debe ser igual o mayor a 0")
    private Double initialAmount;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "client_id", nullable = false)
    @NotNull(message = "El id del cliente es obligatorio")
    private Long clientId;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    public void deposito(Double monto) {
        if (monto == null || monto <= 0) {
            throw new IllegalArgumentException("El monto del deposito debe ser mayor a cero");
        }
        this.initialAmount = this.initialAmount + monto;
    }

    public void retiro(Double monto) {
        if (monto == null || monto <= 0)
            throw new IllegalArgumentException("El monto del retiro debe ser mayor a cero");

        if (this.initialAmount.compareTo(monto) < 0)
            throw new InsufficienteAmountException(this.initialAmount, monto);

        this.initialAmount = this.initialAmount - monto;
    }

    public Double getBalance() {
        return this.initialAmount;
    }
}
