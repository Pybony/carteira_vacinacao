package io.schneider.carteira.vacinacao.controller.dto;

import io.schneider.carteira.vacinacao.shared.model.SexoEnum;

import java.time.LocalDate;

public record ParametrosPessoaDTO(String nome, LocalDate dataNascimento, SexoEnum sexo) {
}
