package io.schneider.carteira.vacinacao.controller;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosPessoaDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoPessoaDTO;
import io.schneider.carteira.vacinacao.domain.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/api/pessoas")
@Tag(name = "Pessoa", description = "Operações relacionadas a pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService service;

    @PostMapping
    @Operation(summary = "Cadastrar nova pessoa", description = "Cadastra uma nova pessoa no sistema")
    public ResponseEntity<RetornoPessoaDTO> inserir(@RequestBody ParametrosPessoaDTO parametrosPessoa){
        final var pessoa = service.salvar(parametrosPessoa);
        final var uri = URI.create("/api/pessoas/" + pessoa.id());
        return ResponseEntity.created(uri).body(pessoa);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pessoa pelo id", description = "Busca uma pessoa no sistema pelo id")
    public ResponseEntity<RetornoPessoaDTO> buscarPeloId(@PathVariable Long id){
        final var pessoa = service.consultarPorId(id);
        return ResponseEntity.ok(pessoa);
    }

}
