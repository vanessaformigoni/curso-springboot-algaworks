//package com.algaworks.algafoodapi.zdescartados;
//
//import com.algaworks.algafoodapi.domain.Repository.RestauranteRepository;
//import com.algaworks.algafoodapi.domain.service.CadastroCozinhaService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
//import com.algaworks.algafoodapi.domain.model.Cozinha;
//import com.algaworks.algafoodapi.domain.model.Restaurante;
//
//@Service
//public class __RestauranteService {
//    private static final String MSG_RESTAURANTE_NAO_ENCONTRADO
//            = "Não existe um cadastro de restaurante com código %d";
//
//    @Autowired
//    private RestauranteRepository restauranteRepository;
//
//    @Autowired
//    private CadastroCozinhaService cadastroCozinha;
//
//    public Restaurante salvar(Restaurante restaurante) {
//        Long cozinhaId = restaurante.getCozinha().getId();
//
//        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);

//		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
//			.orElseThrow(() -> new EntidadeNaoEncontradaException(
//					String.format("Não existe cadastro de cozinha com código %d", cozinhaId)));

//        restaurante.setCozinha(cozinha);
//
//        return restauranteRepository.save(restaurante);
//    }
//
//    public Restaurante buscarOuFalhar(Long restauranteId) {
//        return restauranteRepository.findById(restauranteId)
//                .orElseThrow(() -> new EntidadeNaoEncontradaException(
//                        String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId)));
//    }
//}
