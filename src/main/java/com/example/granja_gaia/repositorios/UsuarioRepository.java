package com.example.granja_gaia.repositorios;

import com.example.granja_gaia.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findTopByNickname(String nickname);
    Optional<Usuario> findFirstByNickname(String nickname);

    boolean existsByNickname(String nickname);

}
