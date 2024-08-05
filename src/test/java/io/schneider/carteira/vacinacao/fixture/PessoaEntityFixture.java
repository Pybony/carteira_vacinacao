package io.schneider.carteira.vacinacao.fixture;

import io.schneider.carteira.vacinacao.domain.entity.PessoaEntity;
import io.schneider.carteira.vacinacao.shared.model.SexoEnum;

import java.time.LocalDate;
import java.util.Optional;

public interface PessoaEntityFixture {

    static PessoaEntity.PessoaEntityBuilder pessoaEntityEntrada() {
        return PessoaEntity.builder()
                .nome("João")
                .dataNascimento(LocalDate.now())
                .sexo(SexoEnum.MASCULINO);
    }

    static PessoaEntity.PessoaEntityBuilder pessoaEntityRetorno() {
        return pessoaEntityEntrada().id(1L);
    }

    static Optional<PessoaEntity> pessoaEntityOptional() {
        return Optional.of(pessoaEntityRetorno().build());
    }

    static Optional<PessoaEntity> pessoaEntityOptionalVazia() {
        return Optional.empty();
    }

}
