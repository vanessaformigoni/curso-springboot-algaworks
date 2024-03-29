package com.algaworks.algafoodapi.api.assembler;

import com.algaworks.algafoodapi.api.model.input.RestauranteInput;
import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDisassembler { //Desmonta de um entidade de modelo de entrada pra uma entidade de dominio.

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInput restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToDomainObject (RestauranteInput restauranteInput, Restaurante restaurante) {
        //Para evitar Caused by: org.hibernate.HibernateException: identifier of an instance of
        // com.algaworks.algafoodapi.domain.model.Cozinha was altered from 1 to 3
        //para poder referenciar uma nova cozinha e o JPA não achar que estamos tentando alterar o id
        //da cozinha já existente.
        restaurante.setCozinha(new Cozinha());
        if (restaurante.getEndereco()!=null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }

        modelMapper.map(restauranteInput,restaurante);
    }

}
