package io.schneider.carteira.vacinacao.shared.model.erro;

public enum ErroCarteiraVacinacao {
    PESSOA_NAO_ENCONTRADA("Pessoa não encontrada"),
    VACINA_NAO_ENCONTRADA("Vacina não encontrada"),
    REGISTRO_VACINACAO_NAO_ENCONTRADO("Registro de vacinação não encontrado"),
    ESTRATEGIA_VACINACAO_NAO_ENCONTRADA("Nenhuma estratégia de validação encontrada para o esquema de vacinação: %s"),
    DOSES_PERMITIDAS("Esta dose não é permitida para este tipo de vacina"),
    DOSES_APLICADAS("Todas as doses desta vacina já foram aplicadas");


    private final String mensagem;

    ErroCarteiraVacinacao(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMessage(Object... args) {
        return String.format(this.mensagem, args) ;
    }
}
