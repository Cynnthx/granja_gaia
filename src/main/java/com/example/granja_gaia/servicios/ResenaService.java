package com.example.granja_gaia.servicios;

import com.example.granja_gaia.dtos.ResenaDTO;
import com.example.granja_gaia.modelos.Cliente;
import com.example.granja_gaia.modelos.Evento;
import com.example.granja_gaia.modelos.Resena;
import com.example.granja_gaia.repositorios.ClienteRepository;
import com.example.granja_gaia.repositorios.EventoRepository;
import com.example.granja_gaia.repositorios.ResenaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResenaService {

    private final ResenaRepository resenaRepository;
    private final ClienteRepository clienteRepository;
    private final EventoRepository eventoRepository;

    /**
     * Obtener todas las reseñas.
     */
    public List<ResenaDTO> obtenerTodasResenas() {
        return resenaRepository.findAll()
                .stream()
                .map(ResenaDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Obtener reseñas por evento.
     */
    public List<ResenaDTO> obtenerResenasPorEvento(Integer idEvento) {
        return resenaRepository.findByEventoId(idEvento)
                .stream()
                .map(ResenaDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Crear una nueva reseña.
     */
    public Optional<ResenaDTO> crearResena(Integer idCliente, Integer idEvento, String resena, int valoracion) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
        Optional<Evento> eventoOpt = eventoRepository.findById(idEvento);

        if (clienteOpt.isPresent() && eventoOpt.isPresent()) {
            Resena nuevaResena = new Resena();
            nuevaResena.setCliente(clienteOpt.get());
            nuevaResena.setEvento(eventoOpt.get());
            nuevaResena.setResena(resena);
            nuevaResena.setValoracion(valoracion);

            return Optional.of(new ResenaDTO(resenaRepository.save(nuevaResena)));
        }

        return Optional.empty();
    }

    /**
     * Eliminar una reseña.
     */
    public void eliminarResena(Integer id) {
        resenaRepository.deleteById(id);
    }
}
