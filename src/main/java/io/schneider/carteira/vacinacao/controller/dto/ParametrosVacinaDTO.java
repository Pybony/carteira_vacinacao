package io.schneider.carteira.vacinacao.controller.dto;

import io.schneider.carteira.vacinacao.shared.model.EsquemaVacinacaoEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ParametrosVacinaDTO(

        @NotNull(message = "O nome não pode ser nulo")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String nome,

        @NotNull(message = "O esquema de vacinação não pode ser nulo")
        EsquemaVacinacaoEnum esquemaVacinacao
) {

}
