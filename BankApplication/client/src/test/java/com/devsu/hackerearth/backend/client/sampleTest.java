package com.devsu.hackerearth.backend.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.client.controller.ClientController;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@ExtendWith(MockitoExtension.class)
public class sampleTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private ClientDto clientDto;

    @BeforeEach
    void setup() {
        clientDto = new ClientDto(
                1L,
                "0502575160",
                "JOSUE LEON",
                "clavesegura",
                "M",
                28,
                "Riobamba",
                "0983852155",
                true);
    }

    @Test
    void testCrearCliente_Success() {
        when(clientService.create(any(ClientDto.class))).thenReturn(clientDto);

        // Act
        ResponseEntity<ClientDto> response = clientController.create(clientDto);

        // Assert
        ClientDto bodyClientDto = response.getBody();

        assertNotNull(response);
        assertNotNull(bodyClientDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(clientDto, bodyClientDto);
        assertEquals("JOSUE LEON", bodyClientDto.getName());
        verify(clientService, times(1)).create(any(ClientDto.class));
    }

    @Test
    void testObtenerClientesPorId_Success() {
        when(clientService.getById(1L)).thenReturn(clientDto);

        ResponseEntity<ClientDto> response = clientController.get(1L);

        ClientDto bodyClientDto = response.getBody();

        assertNotNull(response);
        assertNotNull(bodyClientDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientDto, bodyClientDto);
        assertEquals("0502575160", bodyClientDto.getDni());
        verify(clientService, times(1)).getById(1L);
    }

    @Test
    void testActualizarCliente_Success() {

        ClientDto updateClient = new ClientDto(
                1L,
                "0502575160",
                "JOSUE LEON ACTUALIZADO",
                "clavesegura",
                "M",
                28,
                "NUEVA DIRECCION",
                "0983852155",
                true);

        when(clientService.update(any(ClientDto.class))).thenReturn(updateClient);

        ResponseEntity<ClientDto> response = clientController.update(1L, updateClient);

        ClientDto bodyClientDto = response.getBody();

        assertNotNull(response);
        assertNotNull(bodyClientDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("JOSUE LEON ACTUALIZADO", bodyClientDto.getName());
        assertEquals("NUEVA DIRECCION", bodyClientDto.getAddress());
        verify(clientService, times(1)).update(any(ClientDto.class));
    }
}
