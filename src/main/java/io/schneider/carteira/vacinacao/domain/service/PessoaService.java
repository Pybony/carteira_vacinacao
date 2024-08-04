package io.schneider.carteira.vacinacao.domain.service;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosPessoaDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoPessoaDTO;
import io.schneider.carteira.vacinacao.domain.mapper.PessoaMapper;
import io.schneider.carteira.vacinacao.domain.repository.PessoaRepository;
import io.schneider.carteira.vacinacao.shared.model.exception.AplicativoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository repository;
    private final PessoaMapper mapper;

    public RetornoPessoaDTO salvar(final ParametrosPessoaDTO dto) {
        final var pessoa = mapper.paraEntity(dto);
        final var novaPessoa = repository.save(pessoa);
        return mapper.paraDTO(novaPessoa);
    }

    public RetornoPessoaDTO consultarPorId(final Long id) {
        final var pessoa = repository.findById(id);
        if (pessoa.isPresent())
            return mapper.paraDTO(pessoa.get());
        throw new AplicativoException("Pessoa não encontrada");
    }

}
