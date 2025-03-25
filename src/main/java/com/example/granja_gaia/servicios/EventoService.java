package com.example.granja_gaia.servicios;

import com.example.granja_gaia.dtos.EventoDTO;
import com.example.granja_gaia.modelos.Evento;
import com.example.granja_gaia.repositorios.EventoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    // Obtener todos los eventos
    public List<EventoDTO> obtenerEventos() {
        return eventoRepository.findAll().stream()
                .map(EventoDTO::new)
                .collect(Collectors.toList());
    }

    // Obtener un evento por ID
    public Optional<EventoDTO> obtenerEventoPorId(Integer id) {
        return eventoRepository.findById(id).map(EventoDTO::new);
    }

    // Crear un evento
    public EventoDTO crearEvento(Evento evento) {
        return new EventoDTO(eventoRepository.save(evento));
    }

    // Actualizar un evento
    public Optional<EventoDTO> actualizarEvento(Integer id, Evento eventoDetalles) {
        return eventoRepository.findById(id)
                .map(evento -> {
                    evento.setNombre(eventoDetalles.getNombre());
                    evento.setDescripcion(eventoDetalles.getDescripcion());
                    evento.setFecha(eventoDetalles.getFecha());
                    evento.setCapacidad(eventoDetalles.getCapacidad());
                    evento.setPrecio(eventoDetalles.getPrecio());
                    return new EventoDTO(eventoRepository.save(evento));
                });
    }

    // Eliminar un evento
    public void eliminarEvento(Integer id) {
        eventoRepository.deleteById(id);
    }
}
