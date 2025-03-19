package com.example.granja_gaia.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "token_acceso", schema = "granja_gaia", catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"usuario"}) // Excluye 'usuario' para evitar recursividad
@Builder // Permite usar el patrón Builder para crear instancias
public class TokenAcceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "token", nullable = false, length = 500)
    @NotEmpty(message = "El token no puede estar vacío")
    private String token;

    @Column(name = "fecha_creacion", nullable = false)
    @NotNull(message = "La fecha de creación no puede ser nula")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_expiracion", nullable = false)
    @NotNull(message = "La fecha de expiración no puede ser nula")
    private LocalDateTime fechaExpiracion;

    @OneToOne(fetch = FetchType.LAZY) // Relación uno a uno con Usuario
    @JoinColumn(name = "id_usuario", nullable = false)
    @NotNull(message = "El usuario no puede ser nulo")
    private Usuario usuario; // Usuario asociado al token

    // Método toString() personalizado (opcional)
    @Override
    public String toString() {
        return "TokenAcceso{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaExpiracion=" + fechaExpiracion +
                ", usuario=" + (usuario != null ? usuario.getId() : null) + // Evita recursividad en el toString
                '}';
    }
}