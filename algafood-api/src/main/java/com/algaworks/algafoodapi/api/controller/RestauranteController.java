package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.domain.Repository.RestauranteRepository;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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
    public Restaurante buscar(@PathVariable Long restauranteId) {
        return cadastroRestauranteService.buscarOuFalhar(restauranteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante adicionar(@RequestBody Restaurante restaurante) {
        try {
            return cadastroRestauranteService.salvar(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public Restaurante atualizar(@PathVariable Long restauranteId,
                                 @RequestBody Restaurante restaurante) {
        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        BeanUtils.copyProperties(restaurante, restauranteAtual,
                "id", "formasPagamento", "endereco","dataCadastro", "produtos");
        try {
            return cadastroRestauranteService.salvar(restauranteAtual);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @PatchMapping("/{restauranteId}") //Ele vai ensinar futuramente a fazer de forma mais elegante.
    public Restaurante atualizarParcial(@PathVariable Long restauranteId,
                                        @RequestBody Map<String, Object> campos) {

        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        merge(campos, restauranteAtual); //funcao: mesclar os dados do campos para o restaurante atual

        return atualizar(restauranteId, restauranteAtual); //muito bom isso
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

    /*  @GetMapping //Metodo para entender o Lazy Loading
    public List<Restaurante> listar() {
        List<Restaurante> restaurantes = restauranteRepository.findAll();

        System.out.println(restaurantes.get(0).getNome());
        restaurantes.get(0).getFormasPagamento().forEach(System.out::println);

        return restaurantes;
    }*/

   /* @GetMapping //Metodo para entender o Lazy Loading
    public List<Restaurante> listar() {
        List<Restaurante> restaurantes = restauranteRepository.findAll();

        System.out.println("O nome da cozinha é: ");
        System.out.println(restaurantes.get(0).getCozinha().getNome());

        return restaurantes;
    }*/

}
