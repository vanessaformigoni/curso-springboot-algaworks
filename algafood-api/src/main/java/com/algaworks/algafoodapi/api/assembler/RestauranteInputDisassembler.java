package com.algaworks.algafoodapi.api.assembler;

import com.algaworks.algafoodapi.api.input.RestauranteInput;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDisassembler { //Desmonta de um entidade de modelo de entrada pra uma entidade de dominio.

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject (RestauranteInput restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }
}
