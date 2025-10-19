package com.devsu.hackerearth.backend.account.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transactions", indexes = {
		@Index(name = "idx_transaction_account_id", columnList = "account_id"),
		@Index(name = "idx_transaction_date", columnList = "date"),
		@Index(name = "idx_transaction_deleted", columnList = "deleted"),
})
public class Transaction extends Base {

	@Column(name = "date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "Fecha de transacción  obligatoria")
	private Date date;

	@Column(name = "type", nullable = false, length = 8)
	@NotBlank(message = "Tipo de transacción obligatorio")
	@Size(max = 8, message = "El Tipo de Transacción no debe exceder 8 caracteres")
	private String type;

	@Column(name = "amount", nullable = false)
	@NotNull(message = "Monto obligatorio")
	private Double amount;

	@Column(name = "balance", nullable = false)
	@NotNull(message = "Balance obligatorio")
	private Double balance;

	@Column(name = "account_id", nullable = false)
	@NotNull(message = "El id de la cuenta es obligatorio")
	private Long accountId;

	@Column(name = "deleted", nullable = false)
	@Builder.Default
	private Boolean deleted = false;

	public static Transaction deposito(Account account, Double monto) {
		Double newBalance = account.getInitialAmount() + (monto);

		return Transaction.builder()
				.accountId(account.getId())
				.type(TransactionType.DEPOSITO.getValue())
				.amount(monto)
				.date(new Date())
				.balance(newBalance)
				.deleted(false)
				.build();
	}

	public static Transaction retiro(Account account, Double monto) {
		Double newBalance = account.getInitialAmount() - (monto);

		return Transaction.builder()
				.accountId(account.getId())
				.type(TransactionType.RETIRO.getValue())
				.amount(monto)
				.date(new Date())
				.balance(newBalance)
				.deleted(false)
				.build();
	}

}
