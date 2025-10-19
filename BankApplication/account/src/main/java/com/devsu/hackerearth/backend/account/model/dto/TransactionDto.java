package com.devsu.hackerearth.backend.account.model.dto;

import java.util.Date;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

	private Long id;

	@NotNull(message = "Fecha de transacción obligatoria")
	private Date date;

	@NotBlank(message = "Tipo de transacción obligatorio")
	@Pattern(regexp = "^(DEPOSITO|RETIRO)$", message = "El tipo de transacción debe ser 'DEPOSITO' o 'RETIRO'")
	@Size(max = 8, message = "El Tipo de Transacción no debe exceder 8 caracteres")
	private String type;

	@NotNull(message = "Monto obligatorio")
	private Double amount;

	@NotNull(message = "Balance obligatorio")
	private Double balance;

	@NotNull(message = "El id de la cuenta es obligatorio")
	private Long accountId;
}
