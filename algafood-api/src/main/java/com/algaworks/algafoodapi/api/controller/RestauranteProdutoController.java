package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.api.assembler.ProdutoInputDisassembler;
import com.algaworks.algafoodapi.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafoodapi.api.model.ProdutoModel;
import com.algaworks.algafoodapi.api.model.input.ProdutoInput;
import com.algaworks.algafoodapi.domain.model.Produto;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.ProdutoRepository;
import com.algaworks.algafoodapi.domain.service.CadastroProdutoService;
import com.algaworks.algafoodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private ProdutoModelAssembler produtoModelAssembler;

    @Autowired
    private ProdutoInputDisassembler produtoInputDisassembler;

    @GetMapping
    public List<ProdutoModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        List<Produto> todosProdutos = produtoRepository.findByRestaurante(restaurante);

        return produtoModelAssembler.toCollectionModel(todosProdutos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);

        return produtoModelAssembler.toModel(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@PathVariable Long restauranteId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        produto = cadastroProdutoService.salvar(produto);

        return produtoModelAssembler.toModel(produto);
    }

    @PutMapping("/{produtoId}")
    public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);

        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

        produtoAtual = cadastroProdutoService.salvar(produtoAtual);

        return produtoModelAssembler.toModel(produtoAtual);
    }
}
