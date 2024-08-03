package io.schneider.carteira.vacinacao.fixture;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosRegistroVacinacaoDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoRegistroVacinacaoDTO;

import java.time.LocalDate;

import static io.schneider.carteira.vacinacao.fixture.PessoaDTOFixture.retornoPessoaDTO;
import static io.schneider.carteira.vacinacao.fixture.VacinaDTOFixture.retornoVacinaDTO;
import static io.schneider.carteira.vacinacao.shared.model.DoseEnum.PRIMEIRA_DOSE;

public interface RegistroVacinacaoDTOFixture {

    static ParametrosRegistroVacinacaoDTO.ParametrosRegistroVacinacaoDTOBuilder parametrosRegistroVacinacaoDTO() {
        return ParametrosRegistroVacinacaoDTO.builder()
                .pessoaId(1L)
                .vacinaId(1L)
                .dataAplicacao(LocalDate.now())
                .doseAplicada(PRIMEIRA_DOSE);
    }

    static RetornoRegistroVacinacaoDTO.RetornoRegistroVacinacaoDTOBuilder retornoRegistroVacinacaoDTO() {
        return RetornoRegistroVacinacaoDTO.builder()
                .id(1L)
                .pessoa(retornoPessoaDTO().build())
                .vacina(retornoVacinaDTO().build())
                .dataAplicacao(LocalDate.now())
                .doseAplicada(PRIMEIRA_DOSE);
    }

}
