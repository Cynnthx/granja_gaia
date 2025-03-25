package com.example.granja_gaia.repositorios;

import com.example.granja_gaia.modelos.DetallesPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetallesPedidoRepository extends JpaRepository<DetallesPedido, Integer> {
    @Query("SELECT SUM(d.cantidad * d.precioUnitario) FROM DetallesPedido d WHERE d.pedido.id = :idPedido")
    Float obtenerTotalPedido(@Param("idPedido") Integer idPedido);

    List<DetallesPedido> findByPedidoId(Integer idPedido);
}
