package com.example.granja_gaia.controladores;

import com.example.granja_gaia.dtos.InscripcionEventoDTO;
import com.example.granja_gaia.servicios.InscripcionEventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones-eventos")
@RequiredArgsConstructor
public class InscripcionEventoController {

    @Autowired
    private InscripcionEventoService inscripcionEventoService;

    @GetMapping("/all")
    public ResponseEntity<List<InscripcionEventoDTO>> obtenerTodasLasInscripciones() {
        List<InscripcionEventoDTO> inscripciones = inscripcionEventoService.obtenerInscripciones();
        return ResponseEntity.ok(inscripciones);
    }

    /**
     * Inscribir un cliente en un evento
     * @param clienteId
     * @param eventoId
     * @return
     */
    @PostMapping("/cliente/{clienteId}/evento/{eventoId}")
    public ResponseEntity<?> inscribirClienteEnEvento(
            @PathVariable Integer clienteId,
            @PathVariable Integer eventoId) {
        try {
            return inscripcionEventoService.inscribirClienteEnEvento(clienteId, eventoId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<InscripcionEventoDTO>> obtenerInscripcionesPorCliente(
            @PathVariable Integer clienteId) {
        List<InscripcionEventoDTO> inscripciones =
                inscripcionEventoService.obtenerInscripcionesPorCliente(clienteId);
        return ResponseEntity.ok(inscripciones);
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<InscripcionEventoDTO>> obtenerInscripcionesPorEvento(
            @PathVariable Integer eventoId) {
        List<InscripcionEventoDTO> inscripciones =
                inscripcionEventoService.obtenerInscripcionesPorEvento(eventoId);
        return ResponseEntity.ok(inscripciones);
    }

    /**
     * Cancelar inscripción a un evento
     * @param inscripcionId
     * @return
     */
    @DeleteMapping("/{inscripcionId}")
    public ResponseEntity<Void> cancelarInscripcion(
            @PathVariable Integer inscripcionId) {
        inscripcionEventoService.cancelarInscripcion(inscripcionId);
        return ResponseEntity.noContent().build();
    }

}