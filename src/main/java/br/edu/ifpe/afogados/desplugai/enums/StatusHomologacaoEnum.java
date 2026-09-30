package br.edu.ifpe.afogados.desplugai.enums;

public enum StatusHomologacaoEnum {
    PENDENTE("PENDENTE"),
    APROVADO("APROVADO"),
    REPROVADO("REPROVADO");

    private final String descricao;

    StatusHomologacaoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
