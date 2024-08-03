package io.schneider.carteira.vacinacao.domain.service;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosRegistroVacinacaoDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoRegistroVacinacaoDTO;
import io.schneider.carteira.vacinacao.domain.mapper.RegistroVacinacaoMapper;
import io.schneider.carteira.vacinacao.domain.repository.PessoaRepository;
import io.schneider.carteira.vacinacao.domain.repository.VacinaRepository;
import io.schneider.carteira.vacinacao.domain.repository.RegistroVacinacaoRepository;
import io.schneider.carteira.vacinacao.shared.model.exception.NaoEncontradoExcepiton;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistroVacinacaoService {

    private final RegistroVacinacaoRepository repository;

    private final PessoaRepository pessoaRepository;

    private final VacinaRepository vacinaRepository;

    private final RegistroVacinacaoMapper mapper;

    public RetornoRegistroVacinacaoDTO salvar(final ParametrosRegistroVacinacaoDTO dto) {
        final var registroVacinacao = mapper.paraEntity(dto);

        final var pessoa = pessoaRepository.findById(registroVacinacao.getPessoa().getId())
                .orElseThrow(() -> new NaoEncontradoExcepiton("Pessoa não encontrada"));

        final var vacina = vacinaRepository.findById(registroVacinacao.getVacina().getId())
                .orElseThrow(() -> new NaoEncontradoExcepiton("Vacina não encontrada"));

        final var novoRegistroVacinacao = repository.save(registroVacinacao);

        final var vacinaCompleta = novoRegistroVacinacao.toBuilder()
                .pessoa(pessoa)
                .vacina(vacina)
                .build();

        return mapper.paraDTO(vacinaCompleta);
    }

    public RetornoRegistroVacinacaoDTO consultarPorId(final Long id) {
        final var registroVacinacao = repository.findById(id)
                .orElseThrow(() -> new NaoEncontradoExcepiton("Registro de vacinação não encontrada"));

        final var pessoa = pessoaRepository.findById(registroVacinacao.getPessoa().getId())
                .orElseThrow(() -> new NaoEncontradoExcepiton("Pessoa não encontrada"));

        final var vacina = vacinaRepository.findById(registroVacinacao.getVacina().getId())
                .orElseThrow(() -> new NaoEncontradoExcepiton("Vacina não encontrada"));

        final var registroVacinacaoCompleto = registroVacinacao.toBuilder()
                .pessoa(pessoa)
                .vacina(vacina)
                .build();

        return mapper.paraDTO(registroVacinacaoCompleto);
    }

}
