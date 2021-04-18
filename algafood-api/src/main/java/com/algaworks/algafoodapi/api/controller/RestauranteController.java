package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.domain.Repository.RestauranteRepository;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);

        if (restaurante.isPresent()) {
            return ResponseEntity.ok(restaurante.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = cadastroRestauranteService.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
                                       @RequestBody Restaurante restaurante) {
        try {
            Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);

            if (restauranteAtual.isPresent()) {
                BeanUtils.copyProperties(restaurante, restauranteAtual.get(),
                        "id", "formasPagamento", "endereco","dataCadastro", "produtos");

                Restaurante restauranteSalvo = cadastroRestauranteService.salvar(restauranteAtual.get());
                return ResponseEntity.ok(restauranteSalvo);
            }

            return ResponseEntity.notFound().build(); //Aqui é caso não ache o restauranteId

        } catch (EntidadeNaoEncontradaException e) { //Aqui é caso não ache a cozinhaId
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{restauranteId}") //Ele vai ensinar futuramente a fazer de forma mais elegante.
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
                                              @RequestBody Map<String, Object> campos) {

        Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
        if (restauranteAtual.isEmpty()) {
            ResponseEntity.notFound().build();
        }
        merge(campos, restauranteAtual.get()); //funcao: mesclar os dados do campos para o restaurante atual

        return atualizar(restauranteId, restauranteAtual.get()); //muito bom isso
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) { //Aula 4.33 (final)/34
        ObjectMapper objectMapper = new ObjectMapper(); //É do jackson. Faz a conversão (serializacao) de JSON em Ojetos Java e vice versa.
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class); //Converta esses dadosOrigem pra um tipo Restaurante.

        dadosOrigem.forEach((nomePropriedade, valorpropriedade) -> { //Agora eu só percorro o que foi passado pelo consumidor da Api.
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true); //Mesmo a variável sendo privada, quero acessar ela. Quebra de acesso.

            Object novoValor = ReflectionUtils.getField(field,restauranteOrigem);
            ReflectionUtils.setField(field,restauranteDestino,novoValor);

        });
    }

}
