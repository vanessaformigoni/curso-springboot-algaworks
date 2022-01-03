package com.algaworks.algafoodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

//Estendendo o JpaRepository para customizar o repositório base
@NoRepositoryBean
public interface CustomJpaRepository<T,ID> extends JpaRepository<T,ID> {

    Optional<T> buscarPrimeiro();
}
