package com.algaworks.algafoodapi.descartados;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ResolutionException;

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

