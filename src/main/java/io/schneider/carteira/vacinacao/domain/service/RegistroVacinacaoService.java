package io.schneider.carteira.vacinacao.domain.service;

import io.schneider.carteira.vacinacao.controller.dto.*;
import io.schneider.carteira.vacinacao.domain.converter.CarteiraVacinacaoConverter;
import io.schneider.carteira.vacinacao.domain.entity.RegistroVacinacaoEntity;
import io.schneider.carteira.vacinacao.domain.factory.ValidacaoAplicacaoFactory;
import io.schneider.carteira.vacinacao.domain.mapper.RegistroVacinacaoMapper;
import io.schneider.carteira.vacinacao.domain.repository.PessoaRepository;
import io.schneider.carteira.vacinacao.domain.repository.RegistroVacinacaoRepository;
import io.schneider.carteira.vacinacao.domain.repository.VacinaRepository;
import io.schneider.carteira.vacinacao.shared.model.exception.AplicativoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistroVacinacaoService {

    private final RegistroVacinacaoRepository registroVacinacaoRepository;

    private final PessoaRepository pessoaRepository;

    private final VacinaRepository vacinaRepository;

    private final RegistroVacinacaoMapper mapper;

    private final CarteiraVacinacaoConverter converter;

    private final ValidacaoAplicacaoFactory validacaoFactory;

    public RetornoRegistroVacinacaoDTO salvar(final ParametrosRegistroVacinacaoDTO dto) {
        final var registroVacinacao = mapper.paraEntity(dto);

        final var pessoa = pessoaRepository.findById(registroVacinacao.getPessoa().getId())
                .orElseThrow(() -> new AplicativoException("Pessoa não encontrada"));

        final var vacina = vacinaRepository.findById(registroVacinacao.getVacina().getId())
                .orElseThrow(() -> new AplicativoException("Vacina não encontrada"));

        final var strategy = validacaoFactory.getStrategy(vacina.getEsquemaVacinacao());

        final var registrosAnteriores = registroVacinacaoRepository.findByPessoaIdAndVacinaId(pessoa.getId(), vacina.getId());

        strategy.validar(registroVacinacao, registrosAnteriores);

        final var novoRegistroVacinacao = registroVacinacaoRepository.save(registroVacinacao);

        final var vacinaCompleta = novoRegistroVacinacao.toBuilder()
                .pessoa(pessoa)
                .vacina(vacina)
                .build();

        return mapper.paraDTO(vacinaCompleta);
    }

    public RetornoRegistroVacinacaoDTO consultarPorId(final Long id) {
        final var registroVacinacao = registroVacinacaoRepository.findById(id)
                .orElseThrow(() -> new AplicativoException("Registro de vacinação não encontrada"));

        final var pessoa = pessoaRepository.findById(registroVacinacao.getPessoa().getId())
                .orElseThrow(() -> new AplicativoException("Pessoa não encontrada"));

        final var vacina = vacinaRepository.findById(registroVacinacao.getVacina().getId())
                .orElseThrow(() -> new AplicativoException("Vacina não encontrada"));

        final var registroVacinacaoCompleto = registroVacinacao.toBuilder()
                .pessoa(pessoa)
                .vacina(vacina)
                .build();

        return mapper.paraDTO(registroVacinacaoCompleto);
    }

    public RetornoCarteiraVacinacaoDTO consultarCarteiraVacinacao(Long id) {
        final var pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new AplicativoException("Pessoa não encontrada"));

        final var registrosVacinacoes = registroVacinacaoRepository.findByPessoaId(id);
        final var vacinas = buscarVacinas(registrosVacinacoes);

        return converter.paraCarteiraVacinacaoDTO(pessoa, vacinas);
    }

    private List<RegistroVacinacaoEntity> buscarVacinas(Collection<RegistroVacinacaoEntity> registros) {
        return registros.stream()
                .map(registrovacinacao -> registrovacinacao.toBuilder()
                        .vacina(vacinaRepository.findById(registrovacinacao.getVacina().getId())
                                .orElseThrow(() -> new AplicativoException("Vacina não encontrada")))
                        .build())
                .toList();
    }

}
