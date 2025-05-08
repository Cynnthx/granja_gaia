package com.example.granja_gaia.controladores;

import com.example.granja_gaia.dtos.CategoriaDTO;
import com.example.granja_gaia.modelos.Categoria;
import com.example.granja_gaia.servicios.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    @Autowired
    private final CategoriaService categoriaService;

    // Listar todas las categorías
    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        List<CategoriaDTO> categorias = categoriaService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }

    // Obtener una categoría por su ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerCategoriaPorId(@PathVariable Integer id) {
        Optional<CategoriaDTO> categoria = categoriaService.obtenerCategoriaPorId(id);
        return categoria.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva categoría
    @PostMapping("/crear")
    public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setNombre(categoriaDTO.getNombre());
        nuevaCategoria.setDescripcion(categoriaDTO.getDescripcion());
        CategoriaDTO categoriaCreada = categoriaService.crearCategoria(nuevaCategoria);
        return ResponseEntity.ok(categoriaCreada);
    }

    // Editar una categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> editarCategoria(@PathVariable Integer id, @RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());

        Optional<CategoriaDTO> categoriaEditada = categoriaService.editarCategoria(id, categoria);
        return categoriaEditada.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Integer id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
