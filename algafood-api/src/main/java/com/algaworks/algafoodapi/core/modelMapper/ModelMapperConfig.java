package com.algaworks.algafoodapi.core.modelMapper;

import com.algaworks.algafoodapi.api.model.EnderecoModel;
import com.algaworks.algafoodapi.api.model.RestauranteModel;
import com.algaworks.algafoodapi.domain.model.Endereco;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
       // return new ModelMapper();
        var modelMapper =  new ModelMapper();

//        modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class) //caso eu queira alterar o nome da variavel a ser exibida na saida
//                .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete); //primeiro o get depois o set
       var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
               Endereco.class, EnderecoModel.class);

       enderecoToEnderecoModelTypeMap.<String>addMapping(
               enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
               (enderecoNomeDest,value) -> enderecoNomeDest.getCidade().setEstado(value));

        return modelMapper;
    }
}
