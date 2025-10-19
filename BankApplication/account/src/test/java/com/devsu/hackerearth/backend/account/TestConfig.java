package com.devsu.hackerearth.backend.account;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.devsu.hackerearth.backend.account.service.ClientService;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public ClientService clientService() {
        ClientService mockClientService = Mockito.mock(ClientService.class);

        // Configurar el mock para que todos los clientes existan
        Mockito.when(mockClientService.existsClient(Mockito.anyLong()))
                .thenReturn(true);

        return mockClientService;
    }
}
