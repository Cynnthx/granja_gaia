package com.example.granja_gaia.repositorios;

import com.example.granja_gaia.modelos.Resena;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResenaRepository extends JpaRepository<Resena, Integer> {
    List<Resena> findByEventoId(Integer idEvento);

}
