package com.example.granja_gaia.modelos;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "producto", schema = "granja_gaia", catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"categoria"})
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "imagen_url", nullable = false)
    private String imagenUrl;

    @Column(name = "especificaciones", columnDefinition = "TEXT")
    private String especificaciones;

    // Métodos básicos
    @Transient
    public String getEspecificaciones() {
        return this.especificaciones != null ? this.especificaciones : "{}";
    }
    @Transient
    public void setEspecificaciones(String jsonEspecificaciones) {
        this.especificaciones = jsonEspecificaciones;
    }

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "fecha_creacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion = new Date();

    @Column(name = "es_popular", columnDefinition = "boolean default false")
    private Boolean esPopular = false; // Nuevo campo para marcar productos destacados

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;
}