package io.schneider.carteira.vacinacao.domain.strategy;

import io.schneider.carteira.vacinacao.domain.entity.RegistroVacinacaoEntity;
import io.schneider.carteira.vacinacao.shared.model.exception.NegocioException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static io.schneider.carteira.vacinacao.shared.model.DoseEnum.REFORCO_CONTINUO;
import static io.schneider.carteira.vacinacao.shared.model.DoseEnum.SEGUNDO_REFORCO;

@Slf4j
public class ValidacaoReforcoUnicoStrategy extends AbstractValidacaoAplicacaoStrategy {

    @Override
    protected void validarDosePermitida(RegistroVacinacaoEntity registro) {
        final var doseAplicada = registro.getDoseAplicada();

        log.debug("Dose aplicada: {}", doseAplicada);

        if (doseAplicada == SEGUNDO_REFORCO || doseAplicada == REFORCO_CONTINUO)
            throw new NegocioException("Esta dose não é permitida para este tipo de vacina");
    }

    @Override
    protected void validarDosesAplicadas(List<RegistroVacinacaoEntity> registrosAnteriores) {
        final var dosesAplicadas = registrosAnteriores.size();

        log.debug("Doses aplicadas: {}", dosesAplicadas);

        if (dosesAplicadas >= 4)
            throw new NegocioException("Todas as doses desta vacina já foram aplicadas");
    }

}
