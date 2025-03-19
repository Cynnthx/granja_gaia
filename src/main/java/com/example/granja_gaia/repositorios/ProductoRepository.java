package com.example.granja_gaia.repositorios;

import com.example.granja_gaia.modelos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
