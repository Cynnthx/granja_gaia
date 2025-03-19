package com.example.granja_gaia.repositorios;

import com.example.granja_gaia.modelos.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
}
