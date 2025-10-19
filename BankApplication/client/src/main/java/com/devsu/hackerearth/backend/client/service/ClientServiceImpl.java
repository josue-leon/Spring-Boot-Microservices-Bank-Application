package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.hackerearth.backend.client.exception.ClientDeletedException;
import com.devsu.hackerearth.backend.client.exception.ClientNotFoundException;
import com.devsu.hackerearth.backend.client.exception.DuplicateClientException;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	private final PasswordEncoder passwordEncoder;

	public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
		this.clientRepository = clientRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClientDto> getAll() {
		return clientRepository.findByDeletedFalse()
				.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public ClientDto getById(Long id) {
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new ClientNotFoundException(id));

		if (Boolean.TRUE.equals(client.getDeleted()))
			throw new ClientNotFoundException(id);

		return toDto(client);
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		if (clientRepository.existsByDni(clientDto.getDni())) {
			Client existingClient = clientRepository.findByDni(clientDto.getDni()).orElse(null);

			if (existingClient != null && Boolean.TRUE.equals(existingClient.getDeleted())) {
				throw new ClientDeletedException(clientDto.getDni());
			}

			throw new DuplicateClientException(clientDto.getDni());
		}

		Client client = toEntity(clientDto);
		client.setDeleted(false);

		if (client.getIsActive() == null) {
			client.setIsActive(true);
		}

		// Encriptar Password
		client.setPassword(passwordEncoder.encode(clientDto.getPassword()));

		Client saveClient = clientRepository.save(client);

		return toDto(saveClient);
	}

	@Override
	public ClientDto update(ClientDto clientDto) {
		Client existingClient = clientRepository.findById(clientDto.getId())
				.orElseThrow(() -> new ClientNotFoundException(clientDto.getId()));

		if (Boolean.TRUE.equals(existingClient.getDeleted()))
			throw new ClientNotFoundException(clientDto.getDni());

		// Valida si esta intentando cambiar el dni del cliente
		if (!existingClient.getDni().equals(clientDto.getDni()) && clientRepository.existsByDni(clientDto.getDni())) {
			Client clientWithClient = clientRepository.findByDni(clientDto.getDni()).orElse(null);
			if (clientWithClient != null && Boolean.TRUE.equals(clientWithClient.getDeleted()))
				throw new ClientDeletedException(clientDto.getDni());

			throw new DuplicateClientException(clientDto.getDni());
		}

		// Actualizar campos
		existingClient.setDni(clientDto.getDni());
		existingClient.setName(clientDto.getName());
		existingClient.setGender(clientDto.getGender());
		existingClient.setAge(clientDto.getAge());
		existingClient.setAddress(clientDto.getAddress());
		existingClient.setPhone(clientDto.getPhone());
		existingClient.setIsActive(clientDto.getIsActive());

		if (clientDto.getPassword() != null && !clientDto.getPassword().isEmpty())
			existingClient.setPassword(passwordEncoder.encode(clientDto.getPassword()));

		Client updateClient = clientRepository.save(existingClient);
		return toDto(updateClient);
	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		Client existingClient = clientRepository.findById(id)
				.orElse(null);

		if (existingClient == null || partialClientDto == null || partialClientDto.getIsActive() == null
				|| Boolean.FALSE.equals(existingClient.getIsActive()))
			throw new ClientNotFoundException(id);

		if (Boolean.TRUE.equals(existingClient.getDeleted()))
			throw new ClientDeletedException(id);

		if (partialClientDto.getIsActive() != null) {
			existingClient.setIsActive(partialClientDto.getIsActive());
		}

		Client updatedClient = clientRepository.save(existingClient);
		return toDto(updatedClient);
	}

	@Override
	public void deleteById(Long id) {
		System.out.println("Enviando");
		Client client = clientRepository.findById(id).orElse(null);

		if (client == null || client.getDeleted() == null || client.getDeleted() || !client.getIsActive())
			throw new ClientNotFoundException(id);

		client.setDeleted(true);
		// Estado cliente inacivo
		client.setIsActive(false);
		clientRepository.save(client);
	}

	private ClientDto toDto(Client client) {
		return new ClientDto(
				client.getId(),
				client.getDni(),
				client.getName(),
				null,
				// client.getPassword(),
				client.getGender(),
				client.getAge(),
				client.getAddress(),
				client.getPhone(),
				client.getIsActive());
	}

	private Client toEntity(ClientDto dto) {
		Client client = new Client();
		client.setId(client.getId());
		client.setDni(dto.getDni());
		client.setName(dto.getName());
		client.setGender(dto.getGender());
		client.setAge(dto.getAge());
		client.setAddress(dto.getAddress());
		client.setPhone(dto.getPhone());
		client.setIsActive(dto.getIsActive());
		return client;
	}
}
