package io.schneider.carteira.vacinacao.controller.dto;

import io.schneider.carteira.vacinacao.shared.model.DoseEnum;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RetornoRegistroVacinacaoDTO(Long id, RetornoPessoaDTO pessoa, RetornoVacinaDTO vacina,
                                          LocalDate dataAplicacao, DoseEnum doseAplicada) {
}
