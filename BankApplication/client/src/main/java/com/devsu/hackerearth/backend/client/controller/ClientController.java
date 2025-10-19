package com.devsu.hackerearth.backend.client.controller;

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

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	private final ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	// api/clients
	@GetMapping
	public ResponseEntity<List<ClientDto>> getAll() {
		List<ClientDto> clients = clientService.getAll();
		return ResponseEntity.ok(clients);
	}

	// api/clients/{id}
	@GetMapping("/{id}")
	public ResponseEntity<ClientDto> get(@PathVariable Long id) {
		ClientDto client = clientService.getById(id);
		return ResponseEntity.ok(client);
	}

	// api/clients
	@PostMapping
	public ResponseEntity<ClientDto> create(@Valid @RequestBody ClientDto clientDto) {
		ClientDto createdClient = clientService.create(clientDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
	}

	// api/clients/{id}
	@PutMapping("/{id}")
	public ResponseEntity<ClientDto> update(@PathVariable Long id, @Valid @RequestBody ClientDto clientDto) {
		clientDto.setId(id);
		ClientDto updatedClient = clientService.update(clientDto);
		return ResponseEntity.ok(updatedClient);
	}

	@PutMapping
	public ResponseEntity<ClientDto> update(@Valid @RequestBody ClientDto clientDto) {
		ClientDto updatedClient = clientService.update(clientDto);
		return ResponseEntity.ok(updatedClient);
	}

	// api/accounts/{id}
	@PatchMapping("/{id}")
	public ResponseEntity<ClientDto> partialUpdate(@PathVariable Long id,
			@RequestBody PartialClientDto partialClientDto) {
		ClientDto updatedClient = clientService.partialUpdate(id, partialClientDto);
		return ResponseEntity.ok(updatedClient);
	}

	// api/clients/{id}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		clientService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
