package br.edu.ifpe.afogados.desplugai.dto;

import br.edu.ifpe.afogados.desplugai.enums.StatusAfiliacaoEnum;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@JsonPropertyOrder({
        "id",
        "idProfessor",
        "idSecretaria",
        "status",
        "dataVinculacao"
})
@Getter
@Setter
public class ProfessorAfiliacaoDTO {

    private Long id;

    @NotNull(message = "O professor é de preenchimento obrigatório.")
    private Long idProfessor;

    @NotNull(message = "A secretaria é de preenchimento obrigatório.")
    private Long idSecretaria;

    private StatusAfiliacaoEnum status;

    private Timestamp dataVinculacao;
}
