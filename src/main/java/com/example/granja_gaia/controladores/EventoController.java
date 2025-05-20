package com.example.granja_gaia.controladores;

import com.example.granja_gaia.dtos.EventoDTO;
import com.example.granja_gaia.modelos.Evento;
import com.example.granja_gaia.servicios.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    // Endpoints públicos (sin cambios)
    @GetMapping("/all")
    public ResponseEntity<List<EventoDTO>> obtenerTodosLosEventos() {
        List<EventoDTO> eventos = eventoService.obtenerEventos();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> obtenerEventoPorId(@PathVariable Integer id) {
        return eventoService.obtenerEventoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoints de administración (asegurar que funcionen)
    @PostMapping("/crear")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<EventoDTO> crearEvento(@RequestBody Evento evento) {
        EventoDTO nuevoEvento = eventoService.crearEvento(evento);
        return ResponseEntity.ok(nuevoEvento);
    }

    @PutMapping("/editar/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<EventoDTO> actualizarEvento(
            @PathVariable Integer id,
            @RequestBody Evento evento) {
        return eventoService.actualizarEvento(id, evento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> eliminarEvento(@PathVariable Integer id) {
        eventoService.eliminarEvento(id);
        return ResponseEntity.noContent().build();
    }
}