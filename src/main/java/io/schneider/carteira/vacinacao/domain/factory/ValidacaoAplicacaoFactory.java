package io.schneider.carteira.vacinacao.domain.factory;

import io.schneider.carteira.vacinacao.domain.strategy.*;
import io.schneider.carteira.vacinacao.shared.model.EsquemaVacinacaoEnum;
import io.schneider.carteira.vacinacao.shared.model.erro.ErroCarteiraVacinacao;
import io.schneider.carteira.vacinacao.shared.model.exception.AplicativoException;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

import static io.schneider.carteira.vacinacao.shared.model.EsquemaVacinacaoEnum.*;
import static io.schneider.carteira.vacinacao.shared.model.erro.ErroCarteiraVacinacao.ESTRATEGIA_VACINACAO_NAO_ENCONTRADA;

@Component
public class ValidacaoAplicacaoFactory {

    private final Map<EsquemaVacinacaoEnum, ValidacaoAplicacaoStrategy> strategies;

    public ValidacaoAplicacaoFactory() {
        this.strategies = new EnumMap<>(EsquemaVacinacaoEnum.class);
        this.strategies.put(DOSE_UNICA, new ValidacaoDoseUnicaStrategy());
        this.strategies.put(DOSE_DUPLA, new ValidacaoDoseDuplaStrategy());
        this.strategies.put(DOSE_TRIPLA, new ValidacaoDoseTriplaStrategy());
        this.strategies.put(REFORCO_UNICO, new ValidacaoReforcoUnicoStrategy());
        this.strategies.put(REFORCO_DUPLO, new ValidacaoReforcoDuploStrategy());
        this.strategies.put(REFORCO_CONTINUO, new ValidacaoReforcoContinuoStrategy());
    }

    public ValidacaoAplicacaoStrategy getStrategy(EsquemaVacinacaoEnum esquema) {
        ValidacaoAplicacaoStrategy strategy = this.strategies.get(esquema);

        if (strategy == null)
            throw new AplicativoException(ESTRATEGIA_VACINACAO_NAO_ENCONTRADA.getMessage(esquema));

        return strategy;
    }

}
