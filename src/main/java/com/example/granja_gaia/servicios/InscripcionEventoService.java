package com.example.granja_gaia.servicios;

import com.example.granja_gaia.dtos.InscripcionEventoDTO;
import com.example.granja_gaia.modelos.Cliente;
import com.example.granja_gaia.modelos.Evento;
import com.example.granja_gaia.modelos.InscripcionEvento;
import com.example.granja_gaia.repositorios.ClienteRepository;
import com.example.granja_gaia.repositorios.EventoRepository;
import com.example.granja_gaia.repositorios.InscripcionEventoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InscripcionEventoService {

    private final InscripcionEventoRepository inscripcionEventoRepository;
    private final ClienteRepository clienteRepository;
    private final EventoRepository eventoRepository;

    public List<InscripcionEventoDTO> obtenerInscripciones() {
        return inscripcionEventoRepository.findAll().stream()
                .map(InscripcionEventoDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<InscripcionEventoDTO> inscribirClienteEnEvento(Integer idCliente, Integer idEvento) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
        Optional<Evento> eventoOpt = eventoRepository.findById(idEvento);

        if (clienteOpt.isEmpty() || eventoOpt.isEmpty()) {
            return Optional.empty();
        }

        // Verificar si ya está inscrito
        boolean yaInscrito = inscripcionEventoRepository
                .existsByClienteIdAndEventoId(idCliente, idEvento);

        if (yaInscrito) {
            throw new IllegalStateException("El cliente ya está inscrito en este evento");
        }

        // Verificar capacidad del evento
        Evento evento = eventoOpt.get();
        long inscripcionesActuales = inscripcionEventoRepository.countByEventoId(idEvento);
        if (inscripcionesActuales >= evento.getCapacidad()) {
            throw new IllegalStateException("El evento ha alcanzado su capacidad máxima");
        }

        InscripcionEvento inscripcion = new InscripcionEvento();
        inscripcion.setCliente(clienteOpt.get());
        inscripcion.setEvento(evento);

        return Optional.of(new InscripcionEventoDTO(
                inscripcionEventoRepository.save(inscripcion)));
    }

    public List<InscripcionEventoDTO> obtenerInscripcionesPorCliente(Integer idCliente) {
        return inscripcionEventoRepository.findByClienteId(idCliente).stream()
                .map(InscripcionEventoDTO::new)
                .collect(Collectors.toList());
    }

    public List<InscripcionEventoDTO> obtenerInscripcionesPorEvento(Integer idEvento) {
        return inscripcionEventoRepository.findByEventoId(idEvento).stream()
                .map(InscripcionEventoDTO::new)
                .collect(Collectors.toList());
    }

    public void cancelarInscripcion(Integer id) {
        inscripcionEventoRepository.deleteById(id);
    }
}
