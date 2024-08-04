package io.schneider.carteira.vacinacao.controller.dto;

import lombok.Builder;

import java.util.Collection;

@Builder
public record RetornoCarteiraVacinacaoDTO(RetornoPessoaDTO pessoa, Collection<RetornoVacinaAplicadaDTO> vacinas) {
}
