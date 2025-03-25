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

    // Obtener todas las inscripciones
    public List<InscripcionEventoDTO> obtenerInscripciones() {
        return inscripcionEventoRepository.findAll().stream()
                .map(InscripcionEventoDTO::new)
                .collect(Collectors.toList());
    }

    // Inscribir a un cliente en un evento
    public Optional<InscripcionEventoDTO> inscribirClienteEnEvento(Integer idCliente, Integer idEvento) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
        Optional<Evento> eventoOpt = eventoRepository.findById(idEvento);

        if (clienteOpt.isPresent() && eventoOpt.isPresent()) {
            InscripcionEvento inscripcion = new InscripcionEvento();
            inscripcion.setCliente(clienteOpt.get());
            inscripcion.setEvento(eventoOpt.get());

            return Optional.of(new InscripcionEventoDTO(inscripcionEventoRepository.save(inscripcion)));
        }
        return Optional.empty();
    }

    // Obtener inscripciones de un cliente
    public List<InscripcionEventoDTO> obtenerInscripcionesPorCliente(Integer idCliente) {
        return inscripcionEventoRepository.findByClienteId(idCliente).stream()
                .map(InscripcionEventoDTO::new)
                .collect(Collectors.toList());
    }

    // Obtener inscripciones de un evento
    public List<InscripcionEventoDTO> obtenerInscripcionesPorEvento(Integer idEvento) {
        return inscripcionEventoRepository.findByEventoId(idEvento).stream()
                .map(InscripcionEventoDTO::new)
                .collect(Collectors.toList());
    }

    // Cancelar inscripción
    public void cancelarInscripcion(Integer id) {
        inscripcionEventoRepository.deleteById(id);
    }
}
