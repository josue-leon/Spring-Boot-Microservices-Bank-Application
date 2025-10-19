package com.devsu.hackerearth.backend.client.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

	private Long id;

	@NotBlank(message = "DNI Obligatorio")
	@Size(max = 20, message = "DNI, limite 20 caracteres")
	private String dni;

	@NotBlank(message = "Nombre Obligatorio")
	@Size(max = 20, message = "Nombre, limite 100 caracteres")
	private String name;

	@NotBlank(message = "Password Obligatorio")
	@Size(min = 4, max = 20, message = "Password entre 4 y 20 caracteres")
	private String password;

	private String gender;

	@Min(value = 0, message = "Edad debe ser mayor a 0")
	@Max(value = 100, message = "Edad debe ser menor a 100")
	private int age;

	@Size(max = 200, message = "Direccion, limite 200 caracteres")
	private String address;

	@Size(max = 20, message = "Telefono, limite 20 caracteres")
	private String phone;

	private Boolean isActive;
}
