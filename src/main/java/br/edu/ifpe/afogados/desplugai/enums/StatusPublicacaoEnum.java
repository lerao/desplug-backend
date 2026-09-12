package br.edu.ifpe.afogados.desplugai.enums;

public enum StatusPublicacaoEnum {
    RASCUNHO("RASCUNHO"),
    PENDENTE_MODERACAO("PENDENTE DE MODERAÇÃO"),
    PUBLICADO("PUBLICADO"),
    ARQUIVADO("ARQUIVADO");

    private final String descricao;

    StatusPublicacaoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
