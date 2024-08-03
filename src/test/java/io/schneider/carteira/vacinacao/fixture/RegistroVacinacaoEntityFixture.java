package io.schneider.carteira.vacinacao.fixture;

import io.schneider.carteira.vacinacao.domain.entity.PessoaEntity;
import io.schneider.carteira.vacinacao.domain.entity.VacinaEntity;
import io.schneider.carteira.vacinacao.domain.entity.RegistroVacinacaoEntity;

import java.time.LocalDate;
import java.util.Optional;

import static io.schneider.carteira.vacinacao.shared.model.DoseEnum.PRIMEIRA_DOSE;

public interface RegistroVacinacaoEntityFixture {

    static RegistroVacinacaoEntity.RegistroVacinacaoEntityBuilder registroVacinacaoEntityEntrada() {
        return RegistroVacinacaoEntity.builder()
                .pessoa(PessoaEntity.builder().id(1L).build())
                .vacina(VacinaEntity.builder().id(1L).build())
                .dataAplicacao(LocalDate.now())
                .doseAplicada(PRIMEIRA_DOSE);
    }

    static RegistroVacinacaoEntity.RegistroVacinacaoEntityBuilder registroVacinacaoEntityRetorno() {
        return RegistroVacinacaoEntity.builder()
                .id(1L)
                .pessoa(PessoaEntity.builder().id(1L).build())
                .vacina(VacinaEntity.builder().id(1L).build())
                .dataAplicacao(LocalDate.now())
                .doseAplicada(PRIMEIRA_DOSE);
    }

    static Optional<RegistroVacinacaoEntity> registroVacinacaoEntityOptional() {
        return Optional.of(registroVacinacaoEntityRetorno().build());
    }

    static Optional<RegistroVacinacaoEntity> registroVacinacaoEntityOptionalVazia() {
        return Optional.empty();
    }

}
