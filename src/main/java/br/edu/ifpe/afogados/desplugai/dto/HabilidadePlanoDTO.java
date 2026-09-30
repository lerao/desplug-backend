package br.edu.ifpe.afogados.desplugai.dto;

import br.edu.ifpe.afogados.desplugai.entity.HabilidadeBncc;
import br.edu.ifpe.afogados.desplugai.entity.PlanoAula;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({
        "id",
        "plano",
        "habilidade"
})
@Getter
@Setter
public class HabilidadePlanoDTO {

    private Long id;

    @JsonIgnore
    @NotNull(message = "O plano de aula é de preenchimento obrigatório.")
    private PlanoAula plano;

    @NotNull(message = "A habilidade é de preenchimento obrigatório.")
    private HabilidadeBncc habilidade;
}
