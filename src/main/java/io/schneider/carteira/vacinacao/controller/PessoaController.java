package io.schneider.carteira.vacinacao.controller;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosPessoaDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoCarteiraVacinacaoDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoPessoaDTO;
import io.schneider.carteira.vacinacao.domain.service.PessoaService;
import io.schneider.carteira.vacinacao.domain.service.RegistroVacinacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/pessoas")
@Tag(name = "Pessoa", description = "Operações relacionadas a pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    private final RegistroVacinacaoService registroVacinacaoService;

    @PostMapping
    @Operation(summary = "Cadastrar nova pessoa", description = "Cadastra uma nova pessoa no sistema")
    public ResponseEntity<RetornoPessoaDTO> inserir(@Valid @RequestBody final ParametrosPessoaDTO parametrosPessoa) {
        final var pessoa = pessoaService.salvar(parametrosPessoa);
        final var uri = URI.create("/api/pessoas/" + pessoa.id());
        return ResponseEntity.created(uri).body(pessoa);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pessoa pelo id", description = "Busca uma pessoa no sistema pelo id")
    public ResponseEntity<RetornoPessoaDTO> buscarPeloId(@PathVariable final Long id) {
        final var pessoa = pessoaService.consultarPorId(id);
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("/{id}/carteira-vacinacao")
    @Operation(summary = "Buscar a carteira de vacinação", description = "Busca carteira de vacinação de uma pessoa no sistema pelo id")
    public ResponseEntity<RetornoCarteiraVacinacaoDTO> buscarCarteiraVacinacao(@PathVariable final Long id) {
        final var pessoa = registroVacinacaoService.consultarCarteiraVacinacao(id);
        return ResponseEntity.ok(pessoa);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pessoa pelo id", description = "Exclui uma pessoa e todas as suas vacinações do sistema")
    public ResponseEntity<Void> deletarPessoa(@PathVariable final Long id) {
        pessoaService.deletarPessoaComVacinas(id);
        return ResponseEntity.noContent().build();
    }

}
