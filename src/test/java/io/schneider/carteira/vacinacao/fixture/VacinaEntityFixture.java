package io.schneider.carteira.vacinacao.fixture;

import io.schneider.carteira.vacinacao.domain.entity.VacinaEntity;

import java.util.Optional;

import static io.schneider.carteira.vacinacao.shared.model.EsquemaVacinacaoEnum.DOSE_UNICA;

public interface VacinaEntityFixture {

    static VacinaEntity.VacinaEntityBuilder vacinaEntityEntrada() {
        return VacinaEntity.builder()
                .nome("Epatite")
                .esquemaVacinacao(DOSE_UNICA);
    }

    static VacinaEntity.VacinaEntityBuilder vacinaEntityRetorno() {
        return vacinaEntityEntrada().id(1L);
    }

    static Optional<VacinaEntity> vacinaEntityOptional() {
        return Optional.of(vacinaEntityRetorno().build());
    }

    static Optional<VacinaEntity> vacinaEntityOptionalVazia() {
        return Optional.empty();
    }

}
