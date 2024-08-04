package io.schneider.carteira.vacinacao.domain.strategy;

import io.schneider.carteira.vacinacao.domain.entity.RegistroVacinacaoEntity;

import java.util.List;

public sealed interface ValidacaoAplicacaoStrategy permits AbstractValidacaoAplicacaoStrategy {

    void validar(RegistroVacinacaoEntity registro, List<RegistroVacinacaoEntity> registrosAnteriores);

}
