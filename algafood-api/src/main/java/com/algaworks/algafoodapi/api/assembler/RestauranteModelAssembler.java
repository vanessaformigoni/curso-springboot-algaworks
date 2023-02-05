package com.algaworks.algafoodapi.api.assembler;

import com.algaworks.algafoodapi.api.model.CozinhaModel;
import com.algaworks.algafoodapi.api.model.RestauranteModel;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteModelAssembler { //Entidade monta um Model

    @Autowired
    private ModelMapper modelMapper;
    public RestauranteModel toModel(Restaurante restaurante) {
        return modelMapper.map(restaurante,RestauranteModel.class);
    }

    public List<RestauranteModel> toCollectionModel (List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toModel(restaurante))
                .collect(Collectors.toList());
    }
}
