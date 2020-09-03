package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.Repository.CozinhaRepository;
import com.algaworks.algafoodapi.domain.Repository.RestauranteRepository;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    CozinhaRepository cozinhaRepository;

    public Restaurante salvar (Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

        if(cozinha==null) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de cozinha com código %d",cozinhaId));
        }
        restaurante.setId(cozinhaId);
        return restauranteRepository.salvar(restaurante);
    }

}
