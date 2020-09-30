package com.algaworks.algafoodapi.domain.Repository;

import com.algaworks.algafoodapi.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestauranteRepository
        extends JpaRepository<Restaurante,Long>, RestauranteRepositoryQueries {

    List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    //@Query("from Restaurante where nome like %:nome% and cozinha.id=:id" ) /Exemplo 01: criando a query na mao (inativo)
    //Exemplo 02: com o orm.xml (atual em uso).
    List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinhaId);

    //Exemplo 03: Variacoes de prefixos para query methodos (inativo)
    //List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId);

}
