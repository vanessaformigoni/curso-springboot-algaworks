package com.algaworks.algafoodapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante  {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String nome;

    private BigDecimal taxaFrete;

    //@JsonIgnore
   // @JsonIgnoreProperties("hibernateLazyInitializer")
    @ManyToOne//(fetch = FetchType.LAZY) //Só carrega essa associacao caso precise.
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @JsonIgnore
    @Embedded
    private Endereco endereco;

    @JsonIgnore
    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime dataCadastro;

    @JsonIgnore
    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime dataAtualizacao;

    //@JsonIgnore
    @ManyToMany //(fetch = FetchType.EAGER) //Exemplo de como alterar pra Eager Loading - Ja que o padrão ToMany é Lazy -
    @JoinTable(name = "restaurante_forma_pagamento", //para customizar o nome da tabela intermediaria
            joinColumns = @JoinColumn (name = "restaurante_id"), //(Ja que estamos mapeando restaurante) Define o nome da coluna na tabela intermediaria que associa em restaurante
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();
}
