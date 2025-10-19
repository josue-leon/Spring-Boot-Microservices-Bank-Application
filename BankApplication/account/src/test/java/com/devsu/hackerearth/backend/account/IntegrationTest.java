package com.devsu.hackerearth.backend.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.account.exception.EnumCode;
import com.devsu.hackerearth.backend.account.exception.ErrorResponse;
import com.devsu.hackerearth.backend.account.model.AccountType;
import com.devsu.hackerearth.backend.account.model.TransactionType;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
public class IntegrationTest {

        @Autowired
        private TestRestTemplate restTemplate;

        @Autowired
        private AccountRepository accountRepository;

        @Autowired
        private TransactionRepository transactionRepository;

        @BeforeEach
        void setup() {
                // Se resetea antes de cada test
                transactionRepository.deleteAll();
                accountRepository.deleteAll();
        }

        @Test
        void testFlujoIntegracionTransaccion() {
                final String ACCOUNT_NUMBER = "ACC-INTEGRACION-001";

                // PASO 1: Crear Cuenta
                AccountDto newAccount = new AccountDto(
                                null,
                                ACCOUNT_NUMBER,
                                AccountType.AHORRO.getValue(),
                                1000.00,
                                true,
                                1L);

                ResponseEntity<AccountDto> createResponse = restTemplate.postForEntity(
                                "/api/accounts",
                                newAccount,
                                AccountDto.class);

                assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
                AccountDto createdAccount = createResponse.getBody();
                assertNotNull(createdAccount);
                Long accountId = createdAccount.getId();
                assertNotNull(accountId);

                // PASO 2: Realizar Deposito
                TransactionDto deposito = new TransactionDto(
                                null,
                                new Date(),
                                TransactionType.DEPOSITO.getValue(),
                                500.00,
                                1500.00,
                                accountId);

                ResponseEntity<TransactionDto> depositoResponse = restTemplate.postForEntity(
                                "/api/transactions",
                                deposito,
                                TransactionDto.class);

                assertEquals(HttpStatus.CREATED, depositoResponse.getStatusCode());
                assertNotNull(depositoResponse.getBody());

                // PASO 3: Realizar Retiro Valido
                TransactionDto retiro = new TransactionDto(
                                null,
                                new Date(),
                                TransactionType.RETIRO.getValue(),
                                -300.0,
                                0.0,
                                accountId);

                ResponseEntity<TransactionDto> retiroResponse = restTemplate.postForEntity(
                                "/api/transactions",
                                retiro,
                                TransactionDto.class);

                assertEquals(HttpStatus.CREATED, retiroResponse.getStatusCode());
                assertNotNull(retiroResponse.getBody());

                // PASO 4: Verificar estado final de la cuenta
                ResponseEntity<AccountDto> cuentaResponse = restTemplate.getForEntity(
                                "/api/accounts/ " + accountId,
                                AccountDto.class);

                assertEquals(HttpStatus.OK, cuentaResponse.getStatusCode());
                AccountDto cuentaFinal = cuentaResponse.getBody();
                assertNotNull(cuentaFinal);
                assertEquals(ACCOUNT_NUMBER, cuentaFinal.getNumber());
        }

        @Test
        void testBalanceInsuficiente() {
                final String ACCOUNT_NUMBER = "ACC-LOW-BALANCE";

                // PASO 1: Crear Cuenta con saldo bajo
                AccountDto newAccount = new AccountDto(
                                null, ACCOUNT_NUMBER,
                                AccountType.CORRIENTE.getValue(),
                                100.00,
                                true, 1L);

                ResponseEntity<AccountDto> createResponse = restTemplate.postForEntity(
                                "/api/accounts",
                                newAccount, AccountDto.class);

                assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
                AccountDto createdAccount = createResponse.getBody();
                assertNotNull(createdAccount);
                Long accountId = createdAccount.getId();

                // PASO 2: Intentar retiro que excede el saldo
                TransactionDto retiroLargo = new TransactionDto(
                                null,
                                new Date(),
                                TransactionType.RETIRO.getValue(),
                                -500.00,
                                0.0,
                                accountId);

                ResponseEntity<ErrorResponse> retiroResponse = restTemplate.postForEntity(
                                "/api/transactions",
                                retiroLargo,
                                ErrorResponse.class);

                // PASO 3: Verificar que se rechace con HTTP 400
                assertEquals(HttpStatus.BAD_REQUEST, retiroResponse.getStatusCode());

                // PASO 4: Verificar que el mensaje contiene Saldo no disponible
                ErrorResponse errorResponse = retiroResponse.getBody();
                assertNotNull(errorResponse);
                assertNotNull(errorResponse.getMessage());
                assertNotNull(errorResponse.getErrorCode());
                assertEquals(400, errorResponse.getStatus());
                assertTrue(errorResponse.getMessage().contains("Saldo no disponible"));
                assertEquals(EnumCode.TRX_101, errorResponse.getErrorCode());
        }

        @Test
        void testCuentaCRUD_Integracion() {
                final String urlAccounts = "/api/accounts/";
                // CREAR
                AccountDto newAccount = new AccountDto(
                                null,
                                "ACC-CRUD-TEST",
                                AccountType.CORRIENTE.getValue(),
                                5000.00,
                                true,
                                1L);

                ResponseEntity<AccountDto> createResponse = restTemplate.postForEntity(
                                urlAccounts,
                                newAccount,
                                AccountDto.class);

                assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
                AccountDto createdAccountCrud = createResponse.getBody();
                assertNotNull(createdAccountCrud);

                Long accountId = createdAccountCrud.getId();

                // LEER
                ResponseEntity<AccountDto> readResponse = restTemplate.getForEntity(
                                urlAccounts + accountId,
                                AccountDto.class);

                assertEquals(HttpStatus.OK, readResponse.getStatusCode());
                AccountDto readAccount = readResponse.getBody();
                assertNotNull(readAccount);
                assertEquals("ACC-CRUD-TEST", readAccount.getNumber());

                // MODIFICAR
                AccountDto updatedAccount = readAccount;
                updatedAccount.setType(AccountType.AHORRO.getValue());

                HttpEntity<AccountDto> updateRequest = new HttpEntity<>(updatedAccount);

                ResponseEntity<AccountDto> updateResponse = restTemplate.exchange(
                                urlAccounts + accountId,
                                HttpMethod.PUT,
                                updateRequest,
                                AccountDto.class);

                assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
                AccountDto updated = updateResponse.getBody();
                assertNotNull(updated);
                assertEquals(AccountType.AHORRO.getValue(), updated.getType());

                // ELIMINAR
                restTemplate.delete(urlAccounts + accountId);

                // Verificar que fue eliminado
                ResponseEntity<ErrorResponse> deletedResponse = restTemplate.getForEntity(
                                urlAccounts + accountId,
                                ErrorResponse.class);

                assertEquals(HttpStatus.NOT_FOUND, deletedResponse.getStatusCode());
                ErrorResponse deleteError = deletedResponse.getBody();

                assertNotNull(deleteError);
                assertNotNull(deleteError.getMessage());
                assertTrue(deleteError.getMessage().contains("no encontrada")
                                || deleteError.getMessage().contains("not found"));
        }
}