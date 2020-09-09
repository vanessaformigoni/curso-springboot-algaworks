package com.algaworks.algafoodapi.domain.Repository;

import com.algaworks.algafoodapi.domain.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
