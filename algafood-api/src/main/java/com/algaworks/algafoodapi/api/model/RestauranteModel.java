package com.algaworks.algafoodapi.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteModel {

    private Long id;
    private String nome;
    @NotNull
    private BigDecimal taxaFrete;
    private CozinhaModel cozinha;

}
