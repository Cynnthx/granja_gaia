package com.example.granja_gaia.servicios;

import com.example.granja_gaia.dtos.CrearProductoDTO;
import com.example.granja_gaia.dtos.ActualizarProductoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductoServiceTest {

    @Autowired
    private ProductoService productoService;

    @Test
    public void testCrearYActualizarProducto() {
        // Ejemplo 1: Crear producto
        CrearProductoDTO nuevoProducto = new CrearProductoDTO();
        nuevoProducto.setNombre("Manzanas");
        nuevoProducto.setEsPopular(true);
        productoService.crearProducto(nuevoProducto);

        // Ejemplo 2: Actualizar producto
        ActualizarProductoDTO actualizacion = new ActualizarProductoDTO();
        actualizacion.setEsPopular(false);
        productoService.actualizarProducto(1, actualizacion);
    }
}