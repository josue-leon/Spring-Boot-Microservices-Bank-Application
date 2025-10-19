package com.devsu.hackerearth.backend.client.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

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
@Table(name = "clients", indexes = {
		@Index(name = "idx_client_dni", columnList = "dni"),
		@Index(name = "idx_client_deleted", columnList = "deleted"),
		@Index(name = "idx_client_is_active", columnList = "is_active")
})
public class Client extends Person {

	@Column(name = "password", nullable = false, length = 100)
	@NotBlank(message = "La contraseña es obligatoria")
	private String password;

	@Column(name = "is_active", nullable = false)
	@Builder.Default
	private Boolean isActive = true;

	@Column(name = "deleted", nullable = false)
	@Builder.Default
	private Boolean deleted = true;

}
