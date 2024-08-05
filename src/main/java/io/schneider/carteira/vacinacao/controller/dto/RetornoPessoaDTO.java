package io.schneider.carteira.vacinacao.controller.dto;

import io.schneider.carteira.vacinacao.shared.model.SexoEnum;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RetornoPessoaDTO(Long id, String nome, LocalDate dataNascimento, SexoEnum sexo) {
}
