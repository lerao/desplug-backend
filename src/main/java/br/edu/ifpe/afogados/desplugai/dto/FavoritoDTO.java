package br.edu.ifpe.afogados.desplugai.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@JsonPropertyOrder({
        "id",
        "idUsuario",
        "idPlano",
        "dataRegistro"
})
@Getter
@Setter
public class FavoritoDTO {

    private Long id;

    @NotNull(message = "O usuário do favorito é de preenchimento obrigatório.")
    private Long idUsuario;

    @NotNull(message = "O plano do favorito é de preenchimento obrigatório.")
    private Long idPlano;

    private Timestamp dataRegistro;
}