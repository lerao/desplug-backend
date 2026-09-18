package br.edu.ifpe.afogados.desplugai.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@JsonPropertyOrder({
        "id",
        "idPlano",
        "idProfessor",
        "relatoExperiencia",
        "avaliacaoGeral",
        "fotosEvidencias",
        "dataAplicacao",
        "dataRegistro"
})
@Getter
@Setter
public class

FeedbackPraticoDTO {

    private Long id;

    @NotNull(message = "O plano é de preenchimento obrigatório.")
    private Long idPlano;

    @NotNull(message = "O professor é de preenchimento obrigatório.")
    private Long idProfessor;

    @NotBlank(message = "O relato da experiência é de preenchimento obrigatório.")
    private String relatoExperiencia;

    @NotNull(message = "A avaliação geral é de preenchimento obrigatório.")
    @Min(value = 1, message = "A avaliação geral deve ser no mínimo 1.")
    @Max(value = 5, message = "A avaliação geral deve ser no máximo 5.")
    private Integer avaliacaoGeral;

    @NotNull(message = "A quantidade de fotos/evidências é de preenchimento obrigatório.")
    private Integer fotosEvidencias;

    @NotNull(message = "A data de aplicação é de preenchimento obrigatório.")
    private Timestamp dataAplicacao;

    private Timestamp dataRegistro;
}