package com.algaworks.algafoodapi.api.input;

import com.algaworks.algafoodapi.core.validation.Groups;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteInput {
    @NotBlank
    private String nome;
    @NotNull
    @PositiveOrZero
    private BigDecimal taxaFrete;
    @Valid
    @NotNull
    private CozinhaIdInput cozinha;
}
