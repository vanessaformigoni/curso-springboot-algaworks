//package com.algaworks.algafoodapi.api.model.mixin;
//
//import com.algaworks.algafoodapi.domain.model.Cozinha;
//import com.algaworks.algafoodapi.domain.model.Endereco;
//import com.algaworks.algafoodapi.domain.model.FormaPagamento;
//import com.algaworks.algafoodapi.domain.model.Produto;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import java.time.OffsetDateTime;
//import java.util.List;
//
//public abstract class RestauranteMixin { //Exclui porque agora nós temos uma classe que representa o nosso modelo de dominio que é o restaurante model.
//
//    //@JsonIgnore
//    // @JsonIgnoreProperties("hibernateLazyInitializer")
//    @JsonIgnoreProperties(value = "nome", allowGetters = true)
//    private Cozinha cozinha;
//
//    @JsonIgnore
//    private Endereco endereco;
//
//    @JsonIgnore
//    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
//    private OffsetDateTime dataCadastro;
//
//    @JsonIgnore
//    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
//    private OffsetDateTime dataAtualizacao;
//
//    @JsonIgnore
//    //private List<FormaPagamento> formasPagamento = new ArrayList<>();
//    private List<FormaPagamento> formasPagamento;
//
//    @JsonIgnore
//    private List<Produto> produtos;
//
//}