package io.schneider.carteira.vacinacao.controller.dto;

import io.schneider.carteira.vacinacao.shared.model.DoseEnum;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ParametrosRegistroVacinacaoDTO(Long pessoaId, Long vacinaId, LocalDate dataAplicacao, DoseEnum doseAplicada) {

}
