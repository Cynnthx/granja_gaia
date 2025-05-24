package com.example.granja_gaia.repositorios;

import com.example.granja_gaia.modelos.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    @Query("SELECT p FROM Pedido p join fetch p.cliente  WHERE p.estado != 'pendiente' and p.cliente.id = :clienteId")
    List<Pedido> findByClienteOrders(Integer clienteId);


    @Query("SELECT p FROM Pedido p WHERE p.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Pedido> findByFechaBetween(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    @Query("SELECT p FROM Pedido p join fetch p.cliente  WHERE p.estado != 'pendiente'")
    List<Pedido> findAllOrders();
}
