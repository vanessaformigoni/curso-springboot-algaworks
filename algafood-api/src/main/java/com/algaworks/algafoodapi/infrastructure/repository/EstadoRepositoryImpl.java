package com.algaworks.algafoodapi.infrastructure.repository;

import com.algaworks.algafoodapi.domain.Repository.EstadoRepository;
import com.algaworks.algafoodapi.domain.model.Estado;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Estado> listar() {
        return manager.createQuery("from Estado", Estado.class)
                .getResultList();
    }

    @Override
    public Estado buscar(Long id) {
        return manager.find(Estado.class,id);
    }

    @Override
    @Transactional
    public Estado salvar(Estado estado) {
        return manager.merge(estado);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        Estado estado = manager.find(Estado.class,id);
        if(estado ==null) {
            throw new EmptyResultDataAccessException(1);
        }
        manager.remove(estado);
    }
}
