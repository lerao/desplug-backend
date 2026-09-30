package br.edu.ifpe.afogados.desplugai.enums;

public enum TipoAtividadeEnum {
    PLANO_AULA("PLANO DE AULA"),
    ATIVIDADE_DESPLUGADA("ATIVIDADE DESPLUGADA"),
    PROJETO_MAKER("PROJETO MAKER"),
    ATIVIDADE_DIGITAL("ATIVIDADE DIGITAL");

    private final String descricao;

    TipoAtividadeEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
