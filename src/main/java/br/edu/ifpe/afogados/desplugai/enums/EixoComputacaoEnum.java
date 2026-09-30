package br.edu.ifpe.afogados.desplugai.enums;

public enum EixoComputacaoEnum {
    PENSAMENTO_COMPUTACIONAL("PENSAMENTO COMPUTACIONAL"),
    MUNDO_DIGITAL("MUNDO DIGITAL"),
    CULTURA_DIGITAL("CULTURA DIGITAL");

    private final String descricao;

    EixoComputacaoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}