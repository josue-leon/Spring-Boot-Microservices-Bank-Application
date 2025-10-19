package com.devsu.hackerearth.backend.account.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@GetMapping
	public ResponseEntity<List<TransactionDto>> getAll() {
		List<TransactionDto> transactions = transactionService.getAll();
		return ResponseEntity.ok(transactions);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TransactionDto> get(@PathVariable Long id) {
		TransactionDto transaction = transactionService.getById(id);
		return ResponseEntity.ok(transaction);
	}

	@PostMapping
	public ResponseEntity<TransactionDto> create(@Valid @RequestBody TransactionDto transactionDto) {
		TransactionDto transaction = transactionService.create(transactionDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
	}

	@GetMapping("/clients/{clientId}/report")
	public ResponseEntity<List<BankStatementDto>> report(
			@PathVariable Long clientId,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionStart,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionEnd) {

		List<BankStatementDto> bancBankStatement = transactionService.getAllByAccountClientIdAndDateBetween(clientId,
				dateTransactionStart,
				dateTransactionEnd);
		return ResponseEntity.ok(bancBankStatement);
	}
}
