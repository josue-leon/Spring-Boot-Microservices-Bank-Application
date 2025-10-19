package com.devsu.hackerearth.backend.account.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.exception.AccountNotFoundException;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping
	public ResponseEntity<List<AccountDto>> getAll() {
		List<AccountDto> accounts = accountService.getAll();
		return ResponseEntity.ok(accounts);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> get(@PathVariable Long id) {
		try {
			AccountDto account = accountService.getById(id);
			return ResponseEntity.ok(account);
		} catch (AccountNotFoundException ex) {
			return ResponseEntity.notFound().build();
		}

	}

	@PostMapping
	public ResponseEntity<AccountDto> create(@Valid @RequestBody AccountDto accountDto) {
		AccountDto creactedAccount = accountService.create(accountDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(creactedAccount);

	}

	// /*
	@PutMapping("/{id}")
	public ResponseEntity<AccountDto> update(@PathVariable Long id, @Valid @RequestBody AccountDto accountDto) {
		accountDto.setId(id);
		AccountDto updateAccount = accountService.update(accountDto);
		return ResponseEntity.ok(updateAccount);
	}
	// */

	@PatchMapping("/{id}")
	public ResponseEntity<AccountDto> partialUpdate(@PathVariable Long id,
			@RequestBody PartialAccountDto partialAccountDto) {
		AccountDto updateAccount = accountService.partialUpdate(id, partialAccountDto);
		return ResponseEntity.ok(updateAccount);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		accountService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
