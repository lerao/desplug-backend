package br.edu.ifpe.afogados.desplugai.enums;

public enum EtapaEnsinoEnum {
    EDUCACAO_INFANTIL("EDUCAÇÃO INFANTIL"),
    FUNDAMENTAL_INICIAIS("FUNDAMENTAL INICIAIS"),
    FUNDAMENTAL_FINAIS("FUNDAMENTAL FINAIS"),
    ENSINO_MEDIO("ENSINO MÉDIO");

    private final String descricao;

    EtapaEnsinoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
