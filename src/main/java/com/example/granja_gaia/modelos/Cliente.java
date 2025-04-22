package com.example.granja_gaia.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="cliente", schema = "granja_gaia", catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"usuario"})
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="nombre", nullable = false, length = 100)
    private String nombre;
    @Column(name="apellidos", nullable = false, length = 100)
    private String apellidos;
    @Column(name="dni", nullable = false)
    private String dni;
    @Column(name="foto_perfil", nullable = false)
    private String fotoPerfil;
    @Column(name="direccion", nullable = false)
    private String direccion;
    @Column(name="telefono", nullable = false)
    private String telefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
