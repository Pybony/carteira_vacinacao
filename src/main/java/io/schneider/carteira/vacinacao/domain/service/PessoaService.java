package io.schneider.carteira.vacinacao.domain.service;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosPessoaDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoPessoaDTO;
import io.schneider.carteira.vacinacao.domain.entity.PessoaEntity;
import io.schneider.carteira.vacinacao.domain.mapper.PessoaMapper;
import io.schneider.carteira.vacinacao.domain.repository.PessoaRepository;
import io.schneider.carteira.vacinacao.domain.repository.RegistroVacinacaoRepository;
import io.schneider.carteira.vacinacao.shared.model.exception.AplicativoException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static io.schneider.carteira.vacinacao.shared.model.erro.ErroCarteiraVacinacao.PESSOA_NAO_ENCONTRADA;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    private final RegistroVacinacaoRepository registroVacinacaoRepository;

    private final PessoaMapper mapper;

    public RetornoPessoaDTO salvar(final ParametrosPessoaDTO dto) {
        final var pessoa = mapper.paraEntity(dto);
        final var novaPessoa = pessoaRepository.save(pessoa);

        return mapper.paraDTO(novaPessoa);
    }

    public RetornoPessoaDTO consultarPorId(final Long id) {
        final var pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new AplicativoException(PESSOA_NAO_ENCONTRADA.getMessage()));

        return mapper.paraDTO(pessoa);
    }

    @Transactional
    public void deletarPessoaComVacinas(final Long id) {
        PessoaEntity pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new AplicativoException(PESSOA_NAO_ENCONTRADA.getMessage()));

        registroVacinacaoRepository.deleteByPessoa(pessoa);

        pessoaRepository.delete(pessoa);
    }

}
