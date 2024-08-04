package io.schneider.carteira.vacinacao.controller.dto;

import io.schneider.carteira.vacinacao.shared.model.SexoEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ParametrosPessoaDTO(

        @NotNull(message = "O nome não pode ser nulo")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String nome,

        @NotNull(message = "A data de nascimento não pode ser nula")
        @PastOrPresent(message = "A data de nascimento deve ser uma data anterior ao dia de hoje")
        LocalDate dataNascimento,

        @NotNull(message = "O sexo não pode ser nulo")
        SexoEnum sexo
) {

}
