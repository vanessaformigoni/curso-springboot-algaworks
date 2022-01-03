package com.algaworks.algafoodapi.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepositoryQueries;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import static com.algaworks.algafoodapi.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafoodapi.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> find(String nome,
                                  BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
// Incio Exemplo 01
//        var jpql = "from Restaurante where nome like :nome "
//                + "and taxaFrete between :taxaInicial and :taxaFinal"; //Exemplo 01: query jpql montada (inativo)
        //return manager.createQuery(jpql, Restaurante.class //Para o exemplo 01
//                .setParameter("nome", "%" + nome + "%")
//                .setParameter("taxaInicial", taxaFreteInicial)
//                .setParameter("taxaFinal", taxaFreteFinal)
//                .getResultList();
// Fim Exemplo 01

// Inicio Exemplo 02
//        var jpql = new StringBuilder(); //Exemplo 02: jpql com consulta dinamica (inativo)
//        jpql.append("from Restaurante where 0=0 ");
//
//        var parametros = new HashMap<String, Object>();
//
//        if (StringUtils.hasLength(nome)) {
//            jpql.append("and nome like :nome ");
//            parametros.put("nome", "%" + nome + "%");
//        }
//
//        if (taxaFreteInicial != null) {
//            jpql.append("taxaFrate >= :taxaFreteInicial");
//            parametros.put("taxaInicial", taxaFreteInicial);
//        }
//
//        if (taxaFreteFinal != null) {
//            jpql.append("and taxaFrete <= :taxaFinal ");
//            parametros.put("taxaFinal", taxaFreteFinal);
//        }
//
//        TypedQuery<Restaurante> query = manager
//                .createQuery(jpql.toString(), Restaurante.class);
//
//        parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
//
//        return query.getResultList()
// Fim Exemplo 02

        CriteriaBuilder builder = manager.getCriteriaBuilder(); // Exemplo 03: Consulta dinamica usando CriteriaApi //Não funciona o filtro pra nome e nem a dinamica

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class); //clausula from

        var predicates = new ArrayList<Predicate>();

        if(StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if(taxaFreteInicial!=null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if(taxaFreteFinal!=null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0])); //03 and //converte List em array

        TypedQuery<Restaurante> query = manager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {

        return restauranteRepository.findAll(comFreteGratis()
                .and(comNomeSemelhante(nome)));
    }
}