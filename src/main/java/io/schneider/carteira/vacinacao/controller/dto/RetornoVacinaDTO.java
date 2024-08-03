package io.schneider.carteira.vacinacao.controller.dto;

import io.schneider.carteira.vacinacao.shared.model.EsquemaVacinacaoEnum;
import lombok.Builder;

@Builder
public record RetornoVacinaDTO(Long id, String nome, EsquemaVacinacaoEnum esquemaVacinacao) {

}
