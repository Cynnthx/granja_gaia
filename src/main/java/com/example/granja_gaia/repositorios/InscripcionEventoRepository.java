package com.example.granja_gaia.repositorios;

import com.example.granja_gaia.modelos.InscripcionEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscripcionEventoRepository extends JpaRepository<InscripcionEvento, Integer> {
    List<InscripcionEvento> findByClienteId(Integer idCliente);
    List<InscripcionEvento> findByEventoId(Integer idEvento);
    boolean existsByClienteIdAndEventoId(Integer idCliente, Integer idEvento);
    long countByEventoId(Integer idEvento);
    void deleteByEventoId(Integer eventoId);
}
