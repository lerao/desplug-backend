package br.edu.ifpe.afogados.desplugai.dto;

import br.edu.ifpe.afogados.desplugai.entity.SecretariaEducacao;
import br.edu.ifpe.afogados.desplugai.entity.Usuario;
import br.edu.ifpe.afogados.desplugai.enums.StatusAfiliacaoEnum;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@JsonPropertyOrder({
        "id",
        "professor",
        "secretaria",
        "status",
        "dataVinculacao"
})
@Getter
@Setter
public class ProfessorAfiliacaoDTO {

    private Long id;

    @NotNull(message = "O professor é de preenchimento obrigatório.")
    private Usuario professor;

    @NotNull(message = "A secretaria é de preenchimento obrigatório.")
    private SecretariaEducacao secretaria;

    private StatusAfiliacaoEnum status;

    private Timestamp dataVinculacao;
}
