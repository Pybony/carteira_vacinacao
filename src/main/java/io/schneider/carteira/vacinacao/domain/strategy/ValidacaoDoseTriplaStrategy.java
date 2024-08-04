package io.schneider.carteira.vacinacao.domain.strategy;

import io.schneider.carteira.vacinacao.domain.entity.RegistroVacinacaoEntity;
import io.schneider.carteira.vacinacao.shared.model.exception.NegocioException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static io.schneider.carteira.vacinacao.shared.model.DoseEnum.*;

@Slf4j
public class ValidacaoDoseTriplaStrategy extends AbstractValidacaoAplicacaoStrategy {

    @Override
    protected void validarDosePermitida(RegistroVacinacaoEntity registro) {

        final var doseAplicada = registro.getDoseAplicada();

        log.debug("Dose aplicada: {}", doseAplicada);

        if (doseAplicada != PRIMEIRA_DOSE && doseAplicada != SEGUNDA_DOSE
                && doseAplicada != TERCEIRA_DOSE)
            throw new NegocioException("Esta dose não é permitida para este tipo de vacina");
    }

    @Override
    protected void validarDosesAplicadas(List<RegistroVacinacaoEntity> registrosAnteriores) {
        final var dosesAplicadas = registrosAnteriores.size();

        log.debug("Doses aplicadas: {}", dosesAplicadas);

        if (dosesAplicadas >= 3) {
            throw new NegocioException("Todas as doses desta vacina já foram aplicadas");
        }
    }

}