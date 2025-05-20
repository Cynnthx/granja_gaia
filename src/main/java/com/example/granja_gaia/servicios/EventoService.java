package com.example.granja_gaia.servicios;

import com.example.granja_gaia.dtos.EventoDTO;
import com.example.granja_gaia.modelos.Evento;
import com.example.granja_gaia.modelos.InscripcionEvento;
import com.example.granja_gaia.repositorios.EventoRepository;
import com.example.granja_gaia.repositorios.InscripcionEventoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final InscripcionEventoRepository inscripcionEventoRepository;

    // Métodos existentes (se mantienen exactamente igual)
    public List<EventoDTO> obtenerEventos() {
        return eventoRepository.findAll().stream()
                .map(EventoDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<EventoDTO> obtenerEventoPorId(Integer id) {
        return eventoRepository.findById(id).map(EventoDTO::new);
    }

    public EventoDTO crearEvento(Evento evento) {
        // Validación básica
        if(evento.getFecha().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("No se pueden crear eventos en fechas pasadas");
        }
        return new EventoDTO(eventoRepository.save(evento));
    }

    public Optional<EventoDTO> actualizarEvento(Integer id, Evento eventoDetalles) {
        return eventoRepository.findById(id)
                .map(evento -> {
                    evento.setNombre(eventoDetalles.getNombre());
                    evento.setDescripcion(eventoDetalles.getDescripcion());
                    evento.setFecha(eventoDetalles.getFecha());
                    evento.setCapacidad(eventoDetalles.getCapacidad());
                    evento.setPrecio(eventoDetalles.getPrecio());
                    evento.setImagen(eventoDetalles.getImagen());
                    return new EventoDTO(eventoRepository.save(evento));
                });
    }

    public void eliminarEvento(Integer id) {
        // Primero eliminamos las inscripciones asociadas
        inscripcionEventoRepository.deleteByEventoId(id);
        eventoRepository.deleteById(id);
    }

    // Nuevo método adaptado a tu EventoDTO exacto
    public List<EventoDTO> obtenerEventosPorClienteId(Integer clienteId) {
        List<InscripcionEvento> inscripciones = inscripcionEventoRepository.findByClienteId(clienteId);

        return inscripciones.stream()
                .map(inscripcion -> {
                    Evento evento = inscripcion.getEvento();
                    return new EventoDTO(
                            evento.getId(),
                            evento.getNombre(),
                            evento.getDescripcion(),
                            evento.getFecha(),
                            evento.getCapacidad(),
                            evento.getPrecio(),
                            evento.getImagen()
                    );
                })
                .collect(Collectors.toList());
    }


}