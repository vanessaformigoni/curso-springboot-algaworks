package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.Repository.CozinhaRepository;
import com.algaworks.algafoodapi.domain.Repository.RestauranteRepository;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroRestauranteService {

    public static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe cadastro de restaurante com código %d";

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired CadastroCozinhaService cadastroCozinhaService;

    public Restaurante salvar (Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();

        Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);

//        Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
//                .orElseThrow(() -> new EntidadeNaoEncontradaException(
//                        String.format("Não existe cadastro de cozinha com código %d",cozinhaId)));

        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

    public Restaurante buscarOuFalhar (Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException (
                        String.format(MSG_RESTAURANTE_NAO_ENCONTRADO,restauranteId)));
    }

}
