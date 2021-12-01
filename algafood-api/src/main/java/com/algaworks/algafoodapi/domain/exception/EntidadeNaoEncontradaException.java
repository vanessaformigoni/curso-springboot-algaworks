package com.algaworks.algafoodapi.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)//, reason = "Entidade não encontrada")
public class EntidadeNaoEncontradaException extends NegocioException {

    private static final long serialVersionUID = 1L;

    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
