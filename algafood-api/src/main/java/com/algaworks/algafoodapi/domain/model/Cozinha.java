package com.algaworks.algafoodapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table//(name = "tab_cozinhas")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cozinha {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Quem gera a chave é o provedor
    private Long id;

    @Column(nullable = false)
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "cozinha") //nome da varivel na outra classe para o mapeamento bidirecional.
    private List<Restaurante> restaurantes = new ArrayList<>();

}
