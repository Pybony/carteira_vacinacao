package io.schneider.carteira.vacinacao.domain.strategy;

import io.schneider.carteira.vacinacao.domain.entity.RegistroVacinacaoEntity;
import io.schneider.carteira.vacinacao.shared.model.exception.NegocioException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static io.schneider.carteira.vacinacao.shared.model.DoseEnum.PRIMEIRA_DOSE;
import static io.schneider.carteira.vacinacao.shared.model.erro.ErroCarteiraVacinacao.DOSES_PERMITIDAS;

@Slf4j
public class ValidacaoDoseUnicaStrategy extends AbstractValidacaoAplicacaoStrategy {

    @Override
    protected void validarDosePermitida(RegistroVacinacaoEntity novaAplicacao) {
        final var doseAplicada = novaAplicacao.getDoseAplicada();

        log.debug("Dose aplicada: {}", doseAplicada);

        if (doseAplicada != PRIMEIRA_DOSE)
            throw new NegocioException(DOSES_PERMITIDAS.getMessage());
    }

    @Override
    protected void validarDosesAplicadas(List<RegistroVacinacaoEntity> registrosAnteriores, RegistroVacinacaoEntity novaAplicacao) {
        validarAplicacaoMesmaDose(novaAplicacao, registrosAnteriores);
    }

}
