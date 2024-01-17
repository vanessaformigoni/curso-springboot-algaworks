package com.algaworks.algafoodapi.domain.repository;

import com.algaworks.algafoodapi.domain.model.Pedido;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

}