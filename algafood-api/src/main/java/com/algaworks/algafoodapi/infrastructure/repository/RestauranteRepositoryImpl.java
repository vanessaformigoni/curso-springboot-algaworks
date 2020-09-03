package com.algaworks.algafoodapi.infrastructure.repository;

import com.algaworks.algafoodapi.domain.Repository.CozinhaRepository;
import com.algaworks.algafoodapi.domain.Repository.RestauranteRepository;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> listar() {
        return manager.createQuery("from Restaurante", Restaurante.class)
                .getResultList();
    }

    @Override
    public Restaurante buscar(Long id) {
        return manager.find(Restaurante.class,id);
    }

    @Transactional
    @Override
    public Restaurante salvar(Restaurante restaurante) { //nao fiz
        return manager.merge(restaurante);
    }

    @Transactional
    @Override
    public void remover(Restaurante restaurante) { //não fiz
        restaurante = buscar(restaurante.getId());
        manager.remove(restaurante);
    }

}
