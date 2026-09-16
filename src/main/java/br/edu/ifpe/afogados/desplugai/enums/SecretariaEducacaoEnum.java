package br.edu.ifpe.afogados.desplugai.enums;

public enum SecretariaEducacaoEnum {

    MUNICIPAL("MUNICIPAL"),
    ESTADUAL("ESTADUAL");

    private final String descricao;

    SecretariaEducacaoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}