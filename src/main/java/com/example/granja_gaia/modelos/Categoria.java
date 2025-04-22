package com.example.granja_gaia.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="categoria", schema = "granja_gaia", catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Categoria {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="nombre", nullable = false, length = 100)
    private String nombre;
    @Column(name="descripcion", nullable = false)
    private String descripcion;
}
