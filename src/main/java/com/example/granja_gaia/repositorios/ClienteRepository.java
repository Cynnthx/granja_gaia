package com.example.granja_gaia.repositorios;

import com.example.granja_gaia.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
