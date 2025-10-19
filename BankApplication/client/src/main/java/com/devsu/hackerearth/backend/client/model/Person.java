package com.devsu.hackerearth.backend.client.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class Person extends Base {
	@Column(name = "name", nullable = false, length = 100)
	@NotBlank(message = "El nombre es obligatorio")
	private String name;

	// Clave Primaria PK
	@Column(name = "dni", nullable = false, unique = true, length = 20)
	@NotBlank(message = "El DNI es obligatorio")
	private String dni;

	@Column(name = "gender", length = 10)
	private String gender;

	@Column(name = "age")
	@Min(value = 15, message = "La edad debe ser mayor o igual a 15 años")
	@Max(value = 100, message = "La edad debe ser menor o igual a 100 años")
	private Integer age;

	@Column(name = "address", length = 200)
	private String address;

	@Column(name = "phone", length = 20)
	@Pattern(regexp = "[0-9]{6, 20}", message = "El teléfono solo debe contener números")
	private String phone;
}
