package com.example.granja_gaia.repositorios;

import com.example.granja_gaia.modelos.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
