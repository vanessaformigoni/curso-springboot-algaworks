package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.exception.NegocioException;
import com.algaworks.algafoodapi.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafoodapi.domain.model.Usuario;
import com.algaworks.algafoodapi.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void validarSenha(Long id, String novaSenha , String senhaAtual ) {
        Usuario usuario = buscarOuFalhar(id);

        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(novaSenha);
    }

    public Usuario buscarOuFalhar(Long usuarioId) {
        return (usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId)));
    }
}