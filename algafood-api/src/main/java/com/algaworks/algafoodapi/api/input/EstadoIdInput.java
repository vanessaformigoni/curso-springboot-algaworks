package com.algaworks.algafoodapi.api.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class EstadoIdInput {
    @NotNull
    public Long id;
}
