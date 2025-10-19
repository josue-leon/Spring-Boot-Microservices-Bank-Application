package com.devsu.hackerearth.backend.client.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.client.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByDniAndDeletedFalse(String dni);

    boolean existsByDniAndDeletedFalse(String dni);

    List<Client> findByDeletedFalse();

    // Numero de clientes no eliminados (activos)
    long countByDeletedFalse();

    // Verifica si existe un cliente con el dni especifico (incluye eliminados)
    boolean existsByDni(String dni);

    Optional<Client> findByDni(String dni);
}
