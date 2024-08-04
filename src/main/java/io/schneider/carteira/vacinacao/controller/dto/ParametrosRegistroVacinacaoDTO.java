package io.schneider.carteira.vacinacao.controller.dto;

import io.schneider.carteira.vacinacao.shared.model.DoseEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ParametrosRegistroVacinacaoDTO(

        @NotNull(message = "O id da pessoa não pode ser nulo")
        Long pessoaId,

        @NotNull(message = "O id da vacina não pode ser nulo")
        Long vacinaId,

        @NotNull(message = "A data de aplicação não pode ser nula")
        @PastOrPresent(message = "A data de nascimento deve ser uma data anterior ao dia de hoje")
        LocalDate dataAplicacao,

        @NotNull(message = "A dose aplicada não pode ser nula")
        DoseEnum doseAplicada
) {

}
