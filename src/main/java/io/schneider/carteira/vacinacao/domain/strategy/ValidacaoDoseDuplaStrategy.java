package io.schneider.carteira.vacinacao.domain.strategy;

import io.schneider.carteira.vacinacao.domain.entity.RegistroVacinacaoEntity;
import io.schneider.carteira.vacinacao.shared.model.DoseEnum;
import io.schneider.carteira.vacinacao.shared.model.exception.NegocioException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.schneider.carteira.vacinacao.shared.model.DoseEnum.PRIMEIRA_DOSE;
import static io.schneider.carteira.vacinacao.shared.model.DoseEnum.SEGUNDA_DOSE;
import static io.schneider.carteira.vacinacao.shared.model.erro.ErroCarteiraVacinacao.DOSES_PERMITIDAS;

@Slf4j
public class ValidacaoDoseDuplaStrategy extends AbstractValidacaoAplicacaoStrategy {

    @Override
    protected void validarDosePermitida(RegistroVacinacaoEntity novaAplicacao) {
        log.debug("Nova Aplicacao: {}", novaAplicacao);

        switch (novaAplicacao.getDoseAplicada()) {
            case PRIMEIRA_DOSE:
            case SEGUNDA_DOSE:
                break;
            default:
                throw new NegocioException(DOSES_PERMITIDAS.getMessage());
        }
    }

    @Override
    protected void validarDosesAplicadas(List<RegistroVacinacaoEntity> registrosAnteriores, RegistroVacinacaoEntity novaAplicacao) {
        log.debug("Aplicações anteriores: {}", registrosAnteriores);

        validarAplicacaoMesmaDose(novaAplicacao, registrosAnteriores);

        Map<DoseEnum, List<DoseEnum>> doseRequisitos = new HashMap<>();
        doseRequisitos.put(SEGUNDA_DOSE, List.of(PRIMEIRA_DOSE));

        List<DoseEnum> requisitos = doseRequisitos.get(novaAplicacao.getDoseAplicada());
        if (requisitos != null) {
            for (DoseEnum dose : requisitos) {
                validarExistenciaPrevia(dose, criarDosesAplicadasMap(registrosAnteriores));
            }
        }
    }

}
