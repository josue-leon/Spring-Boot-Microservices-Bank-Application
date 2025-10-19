package com.devsu.hackerearth.backend.account.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankStatementDto {

	private Date date;
	private String clientName;
	private String accountNumber;
	private String accountType;
	private Double initialAmount;
	private Boolean isActive;
	private String transactionType;
	private Double amount;
	private Double balance;
}
