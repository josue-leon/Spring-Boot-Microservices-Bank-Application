package com.devsu.hackerearth.backend.account.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

	private Long id;
	private String dni;
	private String name;
	private String gender;
	private Integer age;
	private String address;
	private String phone;
	private Boolean isActive;
}