package com.devsu.hackerearth.backend.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.account.controller.AccountController;
import com.devsu.hackerearth.backend.account.controller.TransactionController;
import com.devsu.hackerearth.backend.account.model.AccountType;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.service.AccountService;
import com.devsu.hackerearth.backend.account.service.TransactionService;

@ExtendWith(MockitoExtension.class)
public class sampleTest {

	@Mock
	private AccountService accountService;

	@Mock
	private TransactionService transactionService;

	@InjectMocks
	private AccountController accountController;

	@InjectMocks
	private TransactionController transactionController;

	private AccountDto cuentaDto;
	// private TransactionDto depositoDto;
	// private TransactionDto retiroDto;

	@BeforeEach
	void setup() {
		cuentaDto = new AccountDto(1L, "ACC-001", AccountType.AHORRO.getValue(), 1000.00, true, 1L);

		/*
		 * depositoDto = new TransactionDto(
		 * 1L,
		 * LocalDateTime.now(),
		 * "DEPOSITO",
		 * 500.00,
		 * 1500.00,
		 * 1L);
		 * 
		 * retiroDto = new TransactionDto(
		 * 1L,
		 * LocalDateTime.now(),
		 * "RETIRO",
		 * -200.00,
		 * 1300.00,
		 * 1L);
		 */
	}

	@Test
	void testCrearCuenta_Success() {
		when(accountService.create(any(AccountDto.class))).thenReturn(cuentaDto);

		ResponseEntity<AccountDto> response = accountController.create(cuentaDto);

		AccountDto bodyAccountDto = response.getBody();

		assertNotNull(response);
		assertNotNull(bodyAccountDto);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(cuentaDto, bodyAccountDto);
		assertEquals("ACC-001", bodyAccountDto.getNumber());
		assertEquals(1000.00, bodyAccountDto.getInitialAmount());
		verify(accountService, times(1)).create(any(AccountDto.class));
	}

	@Test
	void testObtenerCuentaId_Success() {
		when(accountService.getById(1L)).thenReturn(cuentaDto);

		ResponseEntity<AccountDto> response = accountController.get(1L);

		AccountDto bodyAccountDto = response.getBody();

		assertNotNull(response);
		assertNotNull(bodyAccountDto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cuentaDto, bodyAccountDto);
		assertEquals(AccountType.AHORRO.getValue(), bodyAccountDto.getType());
		verify(accountService, times(1)).getById(1L);
	}

	@Test
	void testObtenerTodasLasCuentas_Success() {
		AccountDto accountDto2 = new AccountDto(
				2L, "ACC-002", AccountType.CORRIENTE.getValue(), 2000.00,
				true, 1L);

		List<AccountDto> accounts = Arrays.asList(cuentaDto, accountDto2);

		when(accountService.getAll()).thenReturn(accounts);

		ResponseEntity<List<AccountDto>> response = accountController.getAll();

		List<AccountDto> bodyAccountDto = response.getBody();

		assertNotNull(response);
		assertNotNull(bodyAccountDto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, bodyAccountDto.size());
		verify(accountService, times(1)).getAll();
	}

	@Test
	void testActualizarCuenta_Success() {
		AccountDto updatedAccount = new AccountDto(
				1L, "ACC-001", AccountType.AHORRO.getValue(), 1500.00,
				true, 1L);

		when(accountService.update(any(AccountDto.class))).thenReturn(updatedAccount);

		ResponseEntity<AccountDto> response = accountController.update(1L, updatedAccount);

		AccountDto bodyAccountDto = response.getBody();

		assertNotNull(response);
		assertNotNull(bodyAccountDto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1500.00, bodyAccountDto.getInitialAmount());
		verify(accountService, times(1)).update(any(AccountDto.class));
	}

	@Test
	void testEliminarCuenta_Success() {
		doNothing().when(accountService).deleteById(1L);

		ResponseEntity<Void> response = accountController.delete(1L);

		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(accountService, times(1)).deleteById(1L);
	}
}
