package com.example.granja_gaia.repositorios;

import com.example.granja_gaia.modelos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    // Método para filtrar por categoría
    List<Producto> findByCategoriaId(Integer idCategoria);
    List<Producto> findByPrecioBetween(double precioMin, double precioMax);

    // Método para buscar productos populares
    List<Producto> findByEsPopularTrue();
}
