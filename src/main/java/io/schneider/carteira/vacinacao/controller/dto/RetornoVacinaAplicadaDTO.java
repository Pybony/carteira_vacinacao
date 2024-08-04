package io.schneider.carteira.vacinacao.controller.dto;

import io.schneider.carteira.vacinacao.shared.model.EsquemaVacinacaoEnum;
import lombok.Builder;

import java.util.Collection;

@Builder
public record RetornoVacinaAplicadaDTO(Long id, String nome, EsquemaVacinacaoEnum esquemaVacinacao, Collection<RetornoDoseAplicadaDTO> doses) {
}
