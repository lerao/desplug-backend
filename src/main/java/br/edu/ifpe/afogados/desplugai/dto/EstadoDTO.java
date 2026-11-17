package br.edu.ifpe.afogados.desplugai.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({
        "id",
        "sigla",
        "nome"
})
@Getter
@Setter
public class EstadoDTO {

    public String id;
    public String sigla;
    public String nome;
}
