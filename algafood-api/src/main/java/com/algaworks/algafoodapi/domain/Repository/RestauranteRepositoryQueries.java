package com.algaworks.algafoodapi.domain.Repository;


import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafoodapi.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

    List<Restaurante> find(String nome,
                           BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

}