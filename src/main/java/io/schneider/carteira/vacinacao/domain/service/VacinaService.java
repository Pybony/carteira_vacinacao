package io.schneider.carteira.vacinacao.domain.service;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosVacinaDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoVacinaDTO;
import io.schneider.carteira.vacinacao.domain.mapper.VacinaMapper;
import io.schneider.carteira.vacinacao.domain.repository.VacinaRepository;
import io.schneider.carteira.vacinacao.shared.model.exception.AplicativoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static io.schneider.carteira.vacinacao.shared.model.erro.ErroCarteiraVacinacao.VACINA_NAO_ENCONTRADA;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacinaService {

    private final VacinaRepository repository;

    private final VacinaMapper mapper;

    public RetornoVacinaDTO salvar(final ParametrosVacinaDTO dto) {
        log.debug("Cadastrando vacina: {}", dto);

        final var vacina = mapper.paraEntity(dto);
        final var novaVacina = repository.save(vacina);

        log.debug("Vacina cadastrada com sucesso");

        return mapper.paraDTO(novaVacina);
    }

    public RetornoVacinaDTO consultarPorId(final Long id) {
        log.debug("Consultar vacina: {}", id);

        final var vacina = repository.findById(id)
                .orElseThrow(() -> new AplicativoException(VACINA_NAO_ENCONTRADA.getMessage()));

        log.debug("Vacina encontrada: {}", vacina);

        return mapper.paraDTO(vacina);
    }

}
