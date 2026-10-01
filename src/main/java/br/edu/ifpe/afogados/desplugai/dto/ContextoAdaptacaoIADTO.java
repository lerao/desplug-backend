package br.edu.ifpe.afogados.desplugai.dto;

import br.edu.ifpe.afogados.desplugai.entity.PlanoAula;
import br.edu.ifpe.afogados.desplugai.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonPropertyOrder({
        "id",
        "professor",
        "planoBase",
        "planoGerado",
        "materiaisDisponiveis",
        "perfilTurma",
        "habilidadeFoco",
        "observacoesInstrucoes",
        "respostaIA",
        "tokensConsumidos",
        "dataInteracao"
})
@Getter
@Setter
public class ContextoAdaptacaoIADTO {

    private Long id;

    @NotNull(message = "O professor é de preenchimento obrigatório.")
    private Long idProfessor;

    private Long idPlanoBase;

    private PlanoAula planoGerado;

    private String materiaisDisponiveis;

    private String perfilTurma;

    private String habilidadeFoco;

    private String observacoesInstrucoes;

    private String respostaIA;

    private Integer tokensConsumidos;

    private LocalDateTime dataInteracao;
}
