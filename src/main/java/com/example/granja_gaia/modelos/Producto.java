package com.example.granja_gaia.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="producto", schema = "granja_gaia", catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"categoria"})
public class Producto {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name="descripcion", nullable= false, length = 100)
    private String descripcion;

    @Column(name="precio", nullable = false)
    private Double precio;

    @Column(name="imagen_url", nullable = false)
    private String imagenUrl;

    @Column(name="especificaciones", nullable = false)
    private String especificaciones;

    @Column(name="stock", nullable = false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;
}
