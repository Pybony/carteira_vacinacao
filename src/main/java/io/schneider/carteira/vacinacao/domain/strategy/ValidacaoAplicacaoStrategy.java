package io.schneider.carteira.vacinacao.domain.strategy;

import io.schneider.carteira.vacinacao.domain.entity.RegistroVacinacaoEntity;
import io.schneider.carteira.vacinacao.shared.model.DoseEnum;
import io.schneider.carteira.vacinacao.shared.model.exception.NegocioException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.schneider.carteira.vacinacao.shared.model.erro.ErroCarteiraVacinacao.DOSE_APLICAR_ANTES;
import static io.schneider.carteira.vacinacao.shared.model.erro.ErroCarteiraVacinacao.MESMA_DOSE_JA_APLICADA;

public sealed interface ValidacaoAplicacaoStrategy permits AbstractValidacaoAplicacaoStrategy {

    void validar(RegistroVacinacaoEntity novaAplicacao, List<RegistroVacinacaoEntity> registrosAnteriores);

    default void validarAplicacaoMesmaDose(RegistroVacinacaoEntity novaAplicacao, List<RegistroVacinacaoEntity> registrosAnteriores) {
        boolean mesmaDoseAplicada = registrosAnteriores.stream()
                .anyMatch(registro -> registro.getDoseAplicada() == novaAplicacao.getDoseAplicada());

        if (mesmaDoseAplicada)
            throw new NegocioException(MESMA_DOSE_JA_APLICADA.getMessage());
    }

    default Map<DoseEnum, Boolean> criarDosesAplicadasMap(List<RegistroVacinacaoEntity> dosesAplicadas) {
        return dosesAplicadas.stream()
                .collect(Collectors.toMap(
                        RegistroVacinacaoEntity::getDoseAplicada,
                        dose -> true,
                        (existing, replacement) -> existing
                ));
    }

    default void validarExistenciaPrevia(DoseEnum dose, Map<DoseEnum, Boolean> dosesAplicadasMap) {
        if (!Boolean.TRUE.equals(dosesAplicadasMap.get(dose))) {
            throw new NegocioException(DOSE_APLICAR_ANTES.getMessage(dose));
        }
    }

}
