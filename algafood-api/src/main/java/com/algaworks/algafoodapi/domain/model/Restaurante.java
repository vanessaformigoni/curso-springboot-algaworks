package com.algaworks.algafoodapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante  {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Quem gera a chave é o provedor
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String nome;

    // @Column(name="taxa_frete") //nem esse precisaria
    private BigDecimal taxaFrete;

    @ManyToOne //Muitos restaurantes possuem uma cozinha  (pode ter mais de um restaurante pra cozinha japonesa).
   // @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getTaxaFrete() {
        return taxaFrete;
    }

    public void setTaxaFrete(BigDecimal taxaFrete) {
        this.taxaFrete = taxaFrete;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Restaurante other = (Restaurante) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
