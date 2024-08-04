package io.schneider.carteira.vacinacao.domain.service;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosVacinaDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoVacinaDTO;
import io.schneider.carteira.vacinacao.domain.mapper.VacinaMapper;
import io.schneider.carteira.vacinacao.domain.repository.VacinaRepository;
import io.schneider.carteira.vacinacao.shared.model.erro.ErroCarteiraVacinacao;
import io.schneider.carteira.vacinacao.shared.model.exception.AplicativoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static io.schneider.carteira.vacinacao.shared.model.erro.ErroCarteiraVacinacao.VACINA_NAO_ENCONTRADA;

@Service
@RequiredArgsConstructor
public class VacinaService {

    private final VacinaRepository repository;

    private final VacinaMapper mapper;

    public RetornoVacinaDTO salvar(final ParametrosVacinaDTO dto) {
        final var vacina = mapper.paraEntity(dto);
        final var novaVacina = repository.save(vacina);

        return mapper.paraDTO(novaVacina);
    }

    public RetornoVacinaDTO consultarPorId(final Long id) {
        final var vacina = repository.findById(id)
                .orElseThrow(() -> new AplicativoException(VACINA_NAO_ENCONTRADA.getMessage()));

        return mapper.paraDTO(vacina);
    }

}
