package com.example.granja_gaia.repositorios;

import com.example.granja_gaia.modelos.Cliente;
import com.example.granja_gaia.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {



    Optional<Cliente> findTopByUsuarioId(Integer idUsuario);

    Optional<Cliente> findByUsuario(Usuario usuario);

    // Método para verificar si un DNI ya existe
    boolean existsByDni(String dni);


    Optional<Object> findByUsuarioId(Integer id);
}
