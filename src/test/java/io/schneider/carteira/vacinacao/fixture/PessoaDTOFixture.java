package io.schneider.carteira.vacinacao.fixture;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosPessoaDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoPessoaDTO;
import io.schneider.carteira.vacinacao.shared.model.SexoEnum;

import java.time.LocalDate;

public interface PessoaDTOFixture {

    static ParametrosPessoaDTO.ParametrosPessoaDTOBuilder parametrosPessoaDTO() {
        return ParametrosPessoaDTO.builder()
                .nome("João")
                .dataNascimento(LocalDate.now())
                .sexo(SexoEnum.MASCULINO);
    }

    static RetornoPessoaDTO.RetornoPessoaDTOBuilder retornoPessoaDTO() {
        return RetornoPessoaDTO.builder()
                .id(1L)
                .nome("João")
                .dataNascimento(LocalDate.now())
                .sexo(SexoEnum.MASCULINO);
    }

}
