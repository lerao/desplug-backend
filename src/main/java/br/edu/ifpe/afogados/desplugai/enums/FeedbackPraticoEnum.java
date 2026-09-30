package br.edu.ifpe.afogados.desplugai.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FeedbackPraticoEnum {

    UM(1),
    DOIS(2),
    TRES(3),
    QUATRO(4),
    CINCO(5);

    private final Integer valor;

    FeedbackPraticoEnum(Integer valor) {
        this.valor = valor;
    }

    @JsonValue
    public Integer getValor() {
        return valor;
    }

    @JsonCreator
    public static FeedbackPraticoEnum fromValor(Integer valor) {

        for (FeedbackPraticoEnum avaliacao : values()) {
            if (avaliacao.valor.equals(valor)) {
                return avaliacao;
            }
        }

        throw new IllegalArgumentException(
                "A avaliação deve ser um número entre 1 e 5."
        );
    }
}