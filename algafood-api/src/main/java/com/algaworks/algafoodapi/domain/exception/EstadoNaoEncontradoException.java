package com.algaworks.algafoodapi.domain.exception;

public class EstadoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public EstadoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public EstadoNaoEncontradaException(Long estadoId) {
        
    }
}
