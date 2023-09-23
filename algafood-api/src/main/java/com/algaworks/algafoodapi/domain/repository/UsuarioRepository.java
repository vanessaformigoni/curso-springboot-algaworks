package com.algaworks.algafoodapi.domain.repository;

import com.algaworks.algafoodapi.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
}
