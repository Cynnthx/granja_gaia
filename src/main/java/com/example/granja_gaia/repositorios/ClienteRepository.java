package com.example.granja_gaia.repositorios;

import com.example.granja_gaia.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByUsuarioId(Integer usuarioId);

}
