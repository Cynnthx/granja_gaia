package com.example.granja_gaia.servicios;

import com.example.granja_gaia.dtos.CategoriaDTO;
import com.example.granja_gaia.modelos.Categoria;
import com.example.granja_gaia.repositorios.CategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    /**
     * Listar todas las categorías.
     */
    public List<CategoriaDTO> listarCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Obtener una categoría por su ID.
     */
    public Optional<CategoriaDTO> obtenerCategoriaPorId(Integer id) {
        return categoriaRepository.findById(id)
                .map(CategoriaDTO::new);
    }

    /**
     * Crear una nueva categoría.
     */
    public CategoriaDTO crearCategoria(Categoria categoria) {
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return new CategoriaDTO(categoriaGuardada);
    }

    /**
     * Editar una categoría existente.
     */
    public Optional<CategoriaDTO> editarCategoria(Integer id, Categoria categoriaDetalles) {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNombre(categoriaDetalles.getNombre());
                    categoria.setDescripcion(categoriaDetalles.getDescripcion());
                    return new CategoriaDTO(categoriaRepository.save(categoria));
                });
    }

    /**
     * Eliminar una categoría.
     */
    public void eliminarCategoria(Integer id) {
        categoriaRepository.deleteById(id);
    }
}
