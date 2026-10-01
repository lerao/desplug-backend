package br.edu.ifpe.afogados.desplugai.enums;

public enum EtapaEnsinoEnum {

    EDUCACAO_INFANTIL("Educação Infantil"),
    FUNDAMENTAL_INICIAIS("Ensino Fundamental - Anos Iniciais"),
    FUNDAMENTAL_FINAIS("Ensino Fundamental - Anos Finais"),
    ENSINO_MEDIO("Ensino Médio");

    private final String descricao;

    EtapaEnsinoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
