package io.schneider.carteira.vacinacao.domain.strategy;

import io.schneider.carteira.vacinacao.domain.entity.RegistroVacinacaoEntity;

import java.util.List;

public non-sealed abstract class AbstractValidacaoAplicacaoStrategy implements ValidacaoAplicacaoStrategy {
    @Override
    public void validar(RegistroVacinacaoEntity registro, List<RegistroVacinacaoEntity> registrosAnteriores) {
        validarDosePermitida(registro);
        validarDosesAplicadas(registrosAnteriores);
    }

    protected abstract void validarDosePermitida(RegistroVacinacaoEntity registro);

    protected abstract void validarDosesAplicadas(List<RegistroVacinacaoEntity> registrosAnteriores);

}
