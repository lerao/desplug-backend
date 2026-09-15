package br.edu.ifpe.afogados.desplugai.enums;

public enum StatusAfiliacaoEnum {

    PENDENTE("PENDENTE"),
    VINCULADO("VINCULADO"),
    DESVINCULADO("DESVINCULADO");

    private final String descricao;

    StatusAfiliacaoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }


}
