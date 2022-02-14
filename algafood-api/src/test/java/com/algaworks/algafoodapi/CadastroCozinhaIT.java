package com.algaworks.algafoodapi;

import com.algaworks.algafoodapi.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.service.CadastroCozinhaService;
import com.algaworks.algafoodapi.domain.service.CadastroRestauranteService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaIT { //testaremos a classe de servico //padraoIT pro failsafe

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @LocalServerPort
    private int port;

    //-------------------TESTE DE API-----------------------

    @Before
    public void setUp() {
        enableLoggingOfRequestAndResponseIfValidationFails(); //Faz o log se falhar
        RestAssured.port=port;
        RestAssured.basePath = "/cozinhas";
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() { //Valida o retorno http


        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConter4Cozinhas_QuandoConsutarCozinhas() { //Valida o body

        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .body("", hasSize(4))
                .body("nome", hasItems("Indiana","Tailandesa"));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha(){
        given()
                .body("{ \"nome\":\"Chinesa\" }")
                .contentType(ContentType.JSON) //Conteudo que estou passando
                .accept(ContentType.JSON) //O que eu aceito de volta
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.CREATED.value());

    }

    //-------------------TESTE DE INTEGRAÇÃO----------------

    @Test
    public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() { //esse teste esta poluindo o banco, o ideal sera usar um banco exclusivo
        //cenario
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        //acao
        novaCozinha = cadastroCozinhaService.salvar(novaCozinha);

        //validacao
        novaCozinha = cadastroCozinhaService.buscarOuFalhar(novaCozinha.getId());
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test(expected = ConstraintViolationException.class)
    public void deveFalhar_QuandoCadastrarCozinhaSemNome(){
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);
        novaCozinha = cadastroCozinhaService.salvar(novaCozinha);
    }

    @Test(expected = EntidadeEmUsoException.class)
    public void deveFalhar_QuandoExcluirCozinhaEmUso(){
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");
        cadastroCozinhaService.salvar(novaCozinha);
        Restaurante novoRestaurante = new Restaurante();
        novoRestaurante.setNome("Bar do Joao");
        novoRestaurante.setTaxaFrete(BigDecimal.TEN);
        novoRestaurante.setCozinha(novaCozinha);
        cadastroRestauranteService.salvar(novoRestaurante);
        cadastroCozinhaService.excluir(novaCozinha.getId());
    }

    @Test(expected = EntidadeEmUsoException.class)
    public void deveFalhar_QuandoExcluirCozinhaEmUso2() {
        cadastroCozinhaService.excluir(1L);
    }

    @Test(expected = CozinhaNaoEncontradaException.class)
    public void deveFalhar_QuandoExcluirCozinhaInexistente2() {
        cadastroCozinhaService.excluir(100L);
    }

}
