package br.edu.ifpe.afogados.desplugai.enums;

public enum TipoAtividadeEnum {
    PLANO_AULA("Plano de Aula"),
    ATIVIDADE_DESPLUGADA("Atividade Desplugada"),
    PROJETO_MAKER("Projeto Maker"),
    ATIVIDADE_DIGITAL("Atividade Digital");

    private final String descricao;

    TipoAtividadeEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
