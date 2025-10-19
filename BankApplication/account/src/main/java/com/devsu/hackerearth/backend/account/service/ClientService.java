package com.devsu.hackerearth.backend.account.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devsu.hackerearth.backend.account.model.dto.ClientDto;

@Service
public class ClientService {

    private final RestTemplate restTemplate;

    @Value("${client.service.url:http://localhost:8001}")
    private String clientServiceURL;

    public ClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ClientDto getClientById(Long clientId) {
        String url = clientServiceURL + "/api/clients/" + clientId;
        return restTemplate.getForObject(url, ClientDto.class);
    }

    public boolean existsClient(Long clientId) {
        try {
            getClientById(clientId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
