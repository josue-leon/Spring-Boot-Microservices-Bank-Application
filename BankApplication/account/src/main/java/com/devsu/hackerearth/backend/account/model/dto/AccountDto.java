package com.devsu.hackerearth.backend.account.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

	private Long id;

	@NotBlank(message = "Número de Cuenta obligatorio")
	@Size(max = 20, message = "El Número de Cuenta no debe exceder 20 caracteres")
	private String number;

	@NotBlank(message = "Tipo de cuenta obligatorio")
	@Pattern(regexp = "^(AHORRO|CORRIENTE)$", message = "El tipo de cuenta debe ser AHORRO o CORRIENTE")
	@Size(max = 9, message = "El Tipo de Cuenta no debe exceder 9 caracteres")
	private String type;

	@NotNull(message = "Monto inicial obligatorio")
	@Min(value = 0,  message = "EL monto inicial debe ser igual o mayor a 0")
	private Double initialAmount;

	private Boolean isActive;

	@NotNull(message = "El id del cliente es obligatorio")
	private Long clientId;
}
