package com.example.granja_gaia.controladores;

import com.example.granja_gaia.dtos.EventoDTO;
import com.example.granja_gaia.modelos.Evento;
import com.example.granja_gaia.servicios.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    @Autowired
    private EventoService eventoService;

    // 1. Obtener todos los eventos
    @GetMapping("/all")
    public ResponseEntity<List<EventoDTO>> obtenerTodosLosEventos() {
        List<EventoDTO> eventos = eventoService.obtenerEventos();
        return ResponseEntity.ok(eventos);
    }

    // 2. Obtener un evento por ID
    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> obtenerEventoPorId(@PathVariable Integer id) {
        return eventoService.obtenerEventoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Crear un nuevo evento
    @PostMapping("/crear")
    public ResponseEntity<EventoDTO> crearEvento(@RequestBody Evento evento) {
        EventoDTO nuevoEvento = eventoService.crearEvento(evento);
        return ResponseEntity.ok(nuevoEvento);
    }

    // 4. Actualizar un evento existente
    @PutMapping("/editar/{id}")
    public ResponseEntity<EventoDTO> actualizarEvento(
            @PathVariable Integer id,
            @RequestBody Evento evento) {
        return eventoService.actualizarEvento(id, evento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Eliminar un evento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable Integer id) {
        eventoService.eliminarEvento(id);
        return ResponseEntity.noContent().build();
    }


    // 6. Obtener eventos por cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<EventoDTO>> obtenerEventosPorCliente(
            @PathVariable Integer clienteId) {
        List<EventoDTO> eventos = eventoService.obtenerEventosPorClienteId(clienteId);
        return ResponseEntity.ok(eventos);
    }
}