package io.schneider.carteira.vacinacao.controller.dto;

import io.schneider.carteira.vacinacao.shared.model.EsquemaVacinacaoEnum;
import lombok.Builder;

@Builder
public record ParametrosVacinaDTO(String nome, EsquemaVacinacaoEnum esquemaVacinacao) {

}
