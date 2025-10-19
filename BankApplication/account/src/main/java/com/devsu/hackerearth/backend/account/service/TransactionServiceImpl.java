package com.devsu.hackerearth.backend.account.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.hackerearth.backend.account.exception.AccountNotFoundException;
import com.devsu.hackerearth.backend.account.exception.TransactionNotFoundException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.ClientDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ClientService clientService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository,
            ClientService clientService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.clientService = clientService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionDto> getAll() {

        return transactionRepository.findByDeletedFalse()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionDto getById(Long id) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));

        if (Boolean.TRUE.equals(transaction.getDeleted()))
            throw new TransactionNotFoundException(id);

        return toDto(transaction);
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        Account account = accountRepository.findById(transactionDto.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(transactionDto.getAccountId()));

        if (Boolean.TRUE.equals(account.getDeleted()))
            throw new AccountNotFoundException(transactionDto.getAccountId());

        Double ammount = transactionDto.getAmount();

        Transaction transaction;
        if (ammount > 0) {
            // DEPOSITO
            account.deposito(ammount);
            transaction = Transaction.deposito(account, ammount);
        } else if (ammount < 0) {
            // RETIRO SALDO INSUFICIENTE
            Double montoRetiro = Math.abs(ammount);
            account.retiro(montoRetiro);
            transaction = Transaction.retiro(account, montoRetiro);
        } else {
            throw new IllegalArgumentException("El monto no puede ser cero");
        }

        // Guardar Cuenta
        accountRepository.save(account);
        Transaction saveTransaction = transactionRepository.save(transaction);
        return toDto(saveTransaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {

        Calendar calStart = Calendar.getInstance();
        calStart.setTime(dateTransactionStart);
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 0);
        Date startDateTime = calStart.getTime();

        Calendar calEnd = Calendar.getInstance();
        calStart.setTime(dateTransactionEnd);
        calStart.set(Calendar.HOUR_OF_DAY, 23);
        calStart.set(Calendar.MINUTE, 59);
        calStart.set(Calendar.SECOND, 59);
        calStart.set(Calendar.MILLISECOND, 999);

        Date endDateTime = calEnd.getTime();

        // Cliente
        ClientDto client = clientService.getClientById(clientId);
        List<Account> accounts = accountRepository.findByClientIdAndDeletedFalse(clientId);

        if (accounts.isEmpty())
            throw new IllegalArgumentException("El cliente no tiene cuentas registradas");

        List<Long> accountIds = accounts.stream()
                .map(Account::getId)
                .collect(Collectors.toList());

        List<Transaction> transactions = transactionRepository
                .findByAccountIdInAndDateBetweenAndDeletedFalse(accountIds, startDateTime, endDateTime);

        // Reporte
        return transactions.stream()
                .map(transaction -> {
                    Account account = accounts.stream()
                            .filter(acc -> acc.getId().equals(transaction.getAccountId()))
                            .findFirst()
                            .orElse(null);

                    return BankStatementDto.builder()
                            .date(transaction.getDate())
                            .clientName(client.getName())
                            .accountNumber(account != null ? account.getNumber() : "N/A")
                            .accountType(account != null ? account.getType() : "N/A")
                            .initialAmount(account != null ? account.getInitialAmount() : 0.0)
                            .isActive(account != null ? account.getIsActive() : false)
                            .transactionType(transaction.getType())
                            .amount(transaction.getAmount())
                            .balance(transaction.getBalance())
                            .build();
                }).collect(Collectors.toList());
    }

    private TransactionDto toDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getId(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getBalance(),
                transaction.getAccountId());
    }

}
