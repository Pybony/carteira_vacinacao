package io.schneider.carteira.vacinacao.controller.dto;

import io.schneider.carteira.vacinacao.shared.model.DoseEnum;

import java.time.LocalDate;

public record RetornoVacinacaoDTO(Long id, RetornoVacinaDTO vacina, LocalDate dataAplicacao, DoseEnum doseAplicada) {
}
