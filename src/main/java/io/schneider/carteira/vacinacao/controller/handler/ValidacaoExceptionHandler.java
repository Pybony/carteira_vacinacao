package io.schneider.carteira.vacinacao.controller.handler;

import io.schneider.carteira.vacinacao.controller.dto.RetornoErroDTO;
import io.schneider.carteira.vacinacao.shared.model.exception.AplicativoException;
import io.schneider.carteira.vacinacao.shared.model.exception.NegocioException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ValidacaoExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<RetornoErroDTO>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        final var erros = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> RetornoErroDTO.builder()
                        .campo(error.getField())
                        .mensagem(error.getDefaultMessage())
                        .build())
                .toList();
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<RetornoErroDTO> handleNegocioExceptions(NegocioException ex) {
        final var erro = RetornoErroDTO.builder()
                .mensagem(ex.getMessage())
                .build();
        return ResponseEntity.unprocessableEntity().body(erro);
    }

    @ExceptionHandler(AplicativoException.class)
    public ResponseEntity<RetornoErroDTO> handleAplicativoExceptions(AplicativoException ex) {
        final var erro = RetornoErroDTO.builder()
                .mensagem(ex.getMessage())
                .build();
        return ResponseEntity.internalServerError().body(erro);
    }

}
