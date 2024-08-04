package io.schneider.carteira.vacinacao.shared.model.exception;

public class NegocioException extends RuntimeException {

    public NegocioException(String mensagem) {
        super(mensagem);
    }

}
