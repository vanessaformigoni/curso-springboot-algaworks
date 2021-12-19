package com.algaworks.algafoodapi.domain.model;

import com.algaworks.algafoodapi.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table//(name = "tab_cozinhas")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cozinha {

    @NotNull(groups = Groups.CozinhaId.class)
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Quem gera a chave é o provedor
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "cozinha") //nome da varivel na outra classe para o mapeamento bidirecional.
    private List<Restaurante> restaurantes = new ArrayList<>();

}
