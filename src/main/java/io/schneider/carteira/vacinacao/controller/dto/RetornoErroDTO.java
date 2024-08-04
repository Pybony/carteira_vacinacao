package io.schneider.carteira.vacinacao.controller.dto;

import lombok.Builder;

@Builder
public record RetornoErroDTO(String campo, String mensagem) {
}
