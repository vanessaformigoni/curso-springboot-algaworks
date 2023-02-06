package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafoodapi.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafoodapi.api.model.CozinhaModel;
import com.algaworks.algafoodapi.api.model.RestauranteModel;
import com.algaworks.algafoodapi.api.input.RestauranteInput;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import com.algaworks.algafoodapi.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    public RestauranteController() {
    }

    @GetMapping
    public List<RestauranteModel> listar() {
        return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscar(@PathVariable Long restauranteId) {
       Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        return restauranteModelAssembler.toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

            return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable Long restauranteId,
                                 @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
           // Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput); //agora vamos usar o mapper
            Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);

            restauranteInputDisassembler.copyToDomainObject(restauranteInput,restauranteAtual);

//          BeanUtils.copyProperties(restaurante, restauranteAtual,
//                "id", "formasPagamento", "endereco","dataCadastro", "produtos"); //agora vamos usar o mapper

            return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

    }

//    @PatchMapping("/{restauranteId}") //Ele vai ensinar futuramente a fazer de forma mais elegante.
//    public Restaurante atualizarParcial(@PathVariable Long restauranteId,
//                                        @RequestBody Map<String, Object> campos , HttpServletRequest request) {
//
//        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
//
//        merge(campos, restauranteAtual , request); //funcao: mesclar os dados do campos para o restaurante atual
//        validate(restauranteAtual, "restaurante");
//
//        return atualizar(restauranteId, restauranteAtual); //muito bom isso
//    }

    private void validate(Restaurante restaurante, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);

        validator.validate(restaurante, bindingResult);
        if(bindingResult.hasErrors()) {
            throw new ValidationException(String.valueOf(bindingResult));
        }
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
                       HttpServletRequest request) { //Aula 4.33 (final)/34
        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper(); //É do jackson. Faz a conversão (serializacao) de JSON em Ojetos Java e vice versa.
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class); //Converta esses dadosOrigem pra um tipo Restaurante.


            dadosOrigem.forEach((nomePropriedade, valorpropriedade) -> { //Agora eu só percorro o que foi passado pelo consumidor da Api.
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true); //Mesmo a variável sendo privada, quero acessar ela. Quebra de acesso.

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
                ReflectionUtils.setField(field, restauranteDestino, novoValor);

            });
        }catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(),rootCause, servletServerHttpRequest);
        }
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
