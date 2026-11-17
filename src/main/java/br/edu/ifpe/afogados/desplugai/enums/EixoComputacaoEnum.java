package br.edu.ifpe.afogados.desplugai.enums;

public enum EixoComputacaoEnum {
    PENSAMENTO_COMPUTACIONAL("Pensamento Computacional"),
    MUNDO_DIGITAL("Mundo Digital"),
    CULTURA_DIGITAL("Cultura Digital");

    private final String descricao;

    EixoComputacaoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}