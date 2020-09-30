package com.algaworks.algafoodapi.infrastructure.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.algaworks.algafoodapi.domain.Repository.RestauranteRepositoryQueries;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> find(String nome,
                                  BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
//Incio Exemplo 01
//        var jpql = "from Restaurante where nome like :nome "
//                + "and taxaFrete between :taxaInicial and :taxaFinal"; //Exemplo 01: query jpql montada (inativo)
        //return manager.createQuery(jpql, Restaurante.class //Para o exemplo 01
//                .setParameter("nome", "%" + nome + "%")
//                .setParameter("taxaInicial", taxaFreteInicial)
//                .setParameter("taxaFinal", taxaFreteFinal)
//                .getResultList();
// Fim Exemplo 01

        var jpql = new StringBuilder(); //Exemplo 02: jpql com consulta dinamica (em uso)
        jpql.append("from Restaurante where 0=0 ");

        var parametros = new HashMap<String, Object>();

        if (StringUtils.hasLength(nome)) {
            jpql.append("and nome like :nome ");
            parametros.put("nome", "%" + nome + "%");
        }

        if (taxaFreteInicial != null) {
            jpql.append("taxaFrate >= :taxaFreteInicial");
            parametros.put("taxaInicial", taxaFreteInicial);
        }

        if (taxaFreteFinal != null) {
            jpql.append("and taxaFrete <= :taxaFinal ");
            parametros.put("taxaFinal", taxaFreteFinal);
        }

        TypedQuery<Restaurante> query = manager
                .createQuery(jpql.toString(), Restaurante.class);

        parametros.forEach((chave, valor) -> query.setParameter(chave, valor));

        return query.getResultList();

    }
}