package io.schneider.carteira.vacinacao.controller;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosVacinaDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoVacinaDTO;
import io.schneider.carteira.vacinacao.domain.service.VacinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/vacinas")
@Tag(name = "Vacina", description = "Operações relacionadas a vacinas")
@RequiredArgsConstructor
public class VacinaController {

    private final VacinaService service;

    @PostMapping
    @Operation(summary = "Cadastrar nova vacina", description = "Cadastra uma nova vacina no sistema")
    public ResponseEntity<RetornoVacinaDTO> inserir(@Valid @RequestBody final ParametrosVacinaDTO parametrosVacina) {
        final var vacina = service.salvar(parametrosVacina);
        final var uri = URI.create("/api/vacinas/" + vacina.id());
        return ResponseEntity.created(uri).body(vacina);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar vacina pelo id", description = "Busca uma vacina no sistema pelo id")
    public ResponseEntity<RetornoVacinaDTO> buscarPeloId(@PathVariable final Long id) {
        final var vacina = service.consultarPorId(id);
        return ResponseEntity.ok(vacina);
    }

}
