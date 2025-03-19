package com.example.granja_gaia.repositorios;

import com.example.granja_gaia.modelos.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
