package com.example.granja_gaia.repositorios;

import com.example.granja_gaia.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
