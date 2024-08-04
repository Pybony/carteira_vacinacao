package io.schneider.carteira.vacinacao.domain.strategy;

import io.schneider.carteira.vacinacao.domain.entity.RegistroVacinacaoEntity;
import io.schneider.carteira.vacinacao.shared.model.exception.NegocioException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static io.schneider.carteira.vacinacao.shared.model.DoseEnum.REFORCO_CONTINUO;
import static io.schneider.carteira.vacinacao.shared.model.DoseEnum.SEGUNDO_REFORCO;
import static io.schneider.carteira.vacinacao.shared.model.erro.ErroCarteiraVacinacao.DOSES_APLICADAS;
import static io.schneider.carteira.vacinacao.shared.model.erro.ErroCarteiraVacinacao.DOSES_PERMITIDAS;

@Slf4j
public class ValidacaoReforcoUnicoStrategy extends AbstractValidacaoAplicacaoStrategy {

    @Override
    protected void validarDosePermitida(RegistroVacinacaoEntity registro) {
        final var doseAplicada = registro.getDoseAplicada();

        log.debug("Dose aplicada: {}", doseAplicada);

        if (doseAplicada == SEGUNDO_REFORCO || doseAplicada == REFORCO_CONTINUO)
            throw new NegocioException(DOSES_PERMITIDAS.getMessage());
    }

    @Override
    protected void validarDosesAplicadas(List<RegistroVacinacaoEntity> registrosAnteriores) {
        final var dosesAplicadas = registrosAnteriores.size();

        log.debug("Doses aplicadas: {}", dosesAplicadas);

        if (dosesAplicadas >= 4)
            throw new NegocioException(DOSES_APLICADAS.getMessage());
    }

}
