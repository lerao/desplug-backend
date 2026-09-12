package br.edu.ifpe.afogados.desplugai.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({
        "id",
        "idPlano",
        "idHabilidade"
})
@Getter
@Setter
public class HabilidadePlanoDTO {

    private Long id;

    @NotNull(message = "O plano de aula é de preenchimento obrigatório.")
    private Long idPlano;

    @NotNull(message = "A habilidade é de preenchimento obrigatório.")
    private Long idHabilidade;
}
