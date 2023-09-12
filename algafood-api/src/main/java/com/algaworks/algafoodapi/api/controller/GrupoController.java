package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.api.assembler.GrupoInputDisassembler;
import com.algaworks.algafoodapi.api.assembler.GrupoModelAssembler;
import com.algaworks.algafoodapi.api.model.GrupoModel;
import com.algaworks.algafoodapi.api.model.input.GrupoInput;
import com.algaworks.algafoodapi.domain.model.Grupo;
import com.algaworks.algafoodapi.domain.repository.GrupoRepository;
import com.algaworks.algafoodapi.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @Autowired
    GrupoInputDisassembler grupoInputDisassembler;

    @GetMapping
    public List<GrupoModel> listar()  {
        return grupoModelAssembler.toCollectionModel(grupoRepository.findAll());
    }

    @GetMapping("{grupoId}")
    public GrupoModel buscar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);

        return grupoModelAssembler.toModel(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel adicionar (@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toObject(grupoInput);

        grupo = cadastroGrupoService.salvar(grupo);

        return grupoModelAssembler.toModel(grupo);
    }

    @PutMapping("/{grupoId}")
    public GrupoModel atualizar(@PathVariable Long grupoId,
                                @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = cadastroGrupoService.buscarOuFalhar(grupoId);

        grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);

        grupoAtual = cadastroGrupoService.salvar(grupoAtual);

        return grupoModelAssembler.toModel(grupoAtual);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        cadastroGrupoService.excluir(grupoId);
    }


}
