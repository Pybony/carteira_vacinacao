package io.schneider.carteira.vacinacao.fixture;

import io.schneider.carteira.vacinacao.controller.dto.ParametrosPessoaDTO;
import io.schneider.carteira.vacinacao.controller.dto.ParametrosVacinaDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoPessoaDTO;
import io.schneider.carteira.vacinacao.controller.dto.RetornoVacinaDTO;
import io.schneider.carteira.vacinacao.shared.model.EsquemaVacinacaoEnum;
import io.schneider.carteira.vacinacao.shared.model.SexoEnum;

import java.time.LocalDate;

import static io.schneider.carteira.vacinacao.shared.model.EsquemaVacinacaoEnum.DOSE_UNICA;

public interface VacinaDTOFixture {

    static ParametrosVacinaDTO.ParametrosVacinaDTOBuilder parametrosVacinaDTO() {
        return ParametrosVacinaDTO.builder()
                .nome("Epatite")
                .esquemaVacinacao(DOSE_UNICA);
    }

    static RetornoVacinaDTO.RetornoVacinaDTOBuilder retornoVacinaDTO() {
        return RetornoVacinaDTO.builder()
                .id(1L)
                .nome("Epatite")
                .esquemaVacinacao(DOSE_UNICA);
    }
}
