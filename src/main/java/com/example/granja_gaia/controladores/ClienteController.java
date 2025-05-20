package com.example.granja_gaia.controladores;

import com.example.granja_gaia.dtos.ClienteDTO;
import com.example.granja_gaia.dtos.CrearClienteDTO;
import com.example.granja_gaia.modelos.Cliente;
import com.example.granja_gaia.servicios.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    // Obtener todos los clientes
    @GetMapping("/all")
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        List<ClienteDTO> clientes = clienteService.findAll();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Integer id) {
        Optional<ClienteDTO> cliente = clienteService.findById(id);
        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obtener cliente por usuarioId a Id
    @GetMapping("/usuario/{usuarioId}")
    public ClienteDTO findClienteIdByUsuarioId(@PathVariable Integer usuarioId) {
        return clienteService.findClienteIdByUsuarioId(usuarioId);
    }


    // Crear un nuevo cliente
    @PostMapping("/crear")
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody CrearClienteDTO clienteDTO) {
        ClienteDTO nuevoCliente = clienteService.createCliente(clienteDTO);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    // Actualizar un cliente existente
    @PutMapping("/{clienteId}")
    public ClienteDTO editarCliente(@PathVariable Integer clienteId, @RequestBody @Valid ClienteDTO dto) throws Exception {
        ClienteDTO clienteDTO = clienteService.updateCliente(clienteId, dto);
        return clienteDTO;
    }

    // Eliminar un cliente por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        clienteService.deleteCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}