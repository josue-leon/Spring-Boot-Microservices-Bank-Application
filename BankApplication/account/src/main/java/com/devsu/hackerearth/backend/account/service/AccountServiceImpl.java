package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.hackerearth.backend.account.exception.AccountNotFoundException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientService clientService;

    public AccountServiceImpl(AccountRepository accountRepository, ClientService clientService) {
        this.accountRepository = accountRepository;
        this.clientService = clientService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> getAll() {
        // Get all accounts
        return accountRepository.findByDeletedFalse()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto getById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        if (Boolean.TRUE.equals(account.getDeleted())) {
            throw new AccountNotFoundException(id);
        }
        return toDto(account);
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        // Existe cliente ?
        if (!clientService.existsClient(accountDto.getClientId()))
            throw new IllegalArgumentException("Cliente con id: " + accountDto.getClientId() + " no existe");

        // Cuenta Repetida?
        if (accountRepository.existsByNumberAndDeletedFalse(accountDto.getNumber()))
            throw new IllegalArgumentException("Ya existe una cuenta con ese numero: " + accountDto.getNumber());

        Account account = toEntity(accountDto);
        account.setDeleted(false);

        if (account.getIsActive() == null)
            account.setIsActive(true);

        Account savedAccount = accountRepository.save(account);
        return toDto(savedAccount);
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        Account existingAccount = accountRepository.findById(accountDto.getId())
                .orElseThrow(() -> new AccountNotFoundException(accountDto.getId()));

        if (Boolean.TRUE.equals(existingAccount.getDeleted()))
            throw new AccountNotFoundException(accountDto.getId());

        // Validar numero de cuenta
        if (!existingAccount.getNumber().equals(accountDto.getNumber())) {
            if (accountRepository.existsByNumberAndDeletedFalse(accountDto.getNumber()))
                throw new IllegalArgumentException("Ya existe una cuenta con el numero: " + accountDto.getNumber());
        }

        // Validar que el cliente exista
        if (!existingAccount.getClientId().equals(accountDto.getClientId())) {
            if (!clientService.existsClient(accountDto.getClientId()))
                throw new IllegalArgumentException("No existe cliente con ID: " + accountDto.getClientId());
        }

        existingAccount.setNumber(accountDto.getNumber());
        existingAccount.setType(accountDto.getType());
        existingAccount.setInitialAmount(accountDto.getInitialAmount());
        existingAccount.setIsActive(accountDto.getIsActive());
        existingAccount.setClientId(accountDto.getClientId());

        Account updateAccount = accountRepository.save(existingAccount);
        return toDto(updateAccount);

    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {

        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        if (Boolean.TRUE.equals(existingAccount.getDeleted()))
            throw new AccountNotFoundException(id);

        if (partialAccountDto.getIsActive() != null)
            existingAccount.setIsActive(partialAccountDto.getIsActive());

        Account updateAccount = accountRepository.save(existingAccount);
        return toDto(updateAccount);

    }

    @Override
    public void deleteById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        // OJO REFACTORIZAR, COMPROBACION REDUNDANTE
        if (Boolean.TRUE.equals(account.getDeleted()))
            throw new AccountNotFoundException(id);

        // ELIMINACION LOGICA

        account.setDeleted(true);
        account.setIsActive(false);
        accountRepository.save(account);

    }

    private AccountDto toDto(Account account) {
        return new AccountDto(
                account.getId(),
                account.getNumber(),
                account.getType(),
                account.getInitialAmount(),
                account.getIsActive(),
                account.getClientId());
    }

    private Account toEntity(AccountDto dto) {
        Account account = new Account();
        account.setNumber(dto.getNumber());
        account.setType(dto.getType());
        account.setInitialAmount(dto.getInitialAmount());
        account.setIsActive(dto.getIsActive());
        account.setClientId(dto.getClientId());
        return account;

    }

}
