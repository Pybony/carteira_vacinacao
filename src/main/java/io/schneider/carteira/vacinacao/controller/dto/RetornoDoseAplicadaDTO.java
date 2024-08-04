package io.schneider.carteira.vacinacao.controller.dto;

import io.schneider.carteira.vacinacao.shared.model.DoseEnum;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RetornoDoseAplicadaDTO(Long id, LocalDate dataAplicacao, DoseEnum doseAplicada) {
}
