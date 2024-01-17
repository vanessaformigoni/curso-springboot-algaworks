package com.algaworks.algafoodapi.api.model.input;

import com.algaworks.algafoodapi.api.model.EnderecoModel;
import com.algaworks.algafoodapi.api.model.FormaPagamentoModel;
import com.algaworks.algafoodapi.api.model.ItemPedidoModel;
import com.algaworks.algafoodapi.api.model.RestauranteResumoModel;
import com.algaworks.algafoodapi.api.model.UsuarioModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Setter
@Getter
public class PedidoResumoModel {

    private Long id;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private RestauranteResumoModel restaurante;
    private UsuarioModel cliente;

}