package com.algaworks.algafoodapi.zdescartados;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)//, reason = "Entidade não encontrada") //não vou usar mais
public class __EntidadeNaoEncontradaException extends ResponseStatusException {

    private static final long serialVersionUID = 1L;

    public __EntidadeNaoEncontradaException(HttpStatus status, String mensagem) {
        super(status, mensagem);
    }

    public __EntidadeNaoEncontradaException(String mensagem) {
        this(HttpStatus.NOT_FOUND,mensagem);
    }
}

