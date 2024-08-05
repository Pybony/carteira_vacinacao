package io.schneider.carteira.vacinacao.controller;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosRegistroVacinacaoDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoRegistroVacinacaoDTO;
import io.schneider.carteira.vacinacao.domain.service.RegistroVacinacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/registro-vacinacao")
@Tag(name = "Registro Vacinação", description = "Operações relacionadas a registros de vacinação")
@RequiredArgsConstructor
public class RegistroVacinacaoController {

    private final RegistroVacinacaoService service;

    @PostMapping
    @Operation(summary = "Cadastrar novo registro de vacinação", description = "Cadastra um novo registro de vacinação no sistema")
    public ResponseEntity<RetornoRegistroVacinacaoDTO> inserir(@Valid @RequestBody final ParametrosRegistroVacinacaoDTO parametrosRegistroVacinacao) {
        final var registroVacinacao = service.salvar(parametrosRegistroVacinacao);
        final var uri = URI.create("/api/registro-vacinacao/" + registroVacinacao.id());
        return ResponseEntity.created(uri).body(registroVacinacao);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar registro de vacinação pelo id", description = "Busca um registro de vacinação no sistema pelo id")
    public ResponseEntity<RetornoRegistroVacinacaoDTO> buscarPeloId(@PathVariable final Long id) {
        final var registroVacinacao = service.consultarPorId(id);
        return ResponseEntity.ok(registroVacinacao);
    }

}
