//package com.algaworks.algafoodapi.api.assembler;
//
//import com.algaworks.algafoodapi.api.model.input.UsuarioComSenhaInput;
//import com.algaworks.algafoodapi.domain.model.Usuario;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UsuarioComSenhaInputDisassembler {
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    public Usuario toDomainObject (UsuarioComSenhaInput usuarioComSenhaInput) {
//        return modelMapper.map(usuarioComSenhaInput, Usuario.class);
//    }
//
//
//}
