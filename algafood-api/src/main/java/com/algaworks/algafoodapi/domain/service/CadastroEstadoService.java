package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.repository.EstadoRepository;
import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafoodapi.domain.model.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroEstadoService {

    public static final String MSG_ESTADO_EM_USO =
            "Estado de código %d não pode ser removido, pois está em uso";

    @Autowired
    EstadoRepository estadoRepository;

    @Transactional
    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    @Transactional
    public void excluir(Long estadoId) {
        try {
            estadoRepository.deleteById(estadoId);
            estadoRepository.flush();
        }catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradoException(estadoId);
        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, estadoId));
        }

    }

    public Estado buscarOuFalhar(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(()-> new EstadoNaoEncontradoException(estadoId));
    }
}
