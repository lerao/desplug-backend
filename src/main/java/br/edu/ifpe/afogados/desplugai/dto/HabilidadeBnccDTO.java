package br.edu.ifpe.afogados.desplugai.dto;

import br.edu.ifpe.afogados.desplugai.enums.EixoComputacaoEnum;
import br.edu.ifpe.afogados.desplugai.enums.EtapaEnsinoEnum;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({
        "id",
        "codigo",
        "descricao",
        "eixoComputacao",
        "etapaEnsino",
        "faixaEtaria",
        "componenteCurricular"
})
@Getter
@Setter
public class HabilidadeBnccDTO {

    private Long id;

    @NotBlank(message = "O código da habilidade é de preenchimento obrigatório.")
    private String codigo;

    @NotBlank(message = "A descrição da habilidade é de preenchimento obrigatório.")
    private String descricao;

    @NotNull(message = "O eixo de computação é de preenchimento obrigatório.")
    private EixoComputacaoEnum eixoComputacao;

    @NotNull(message = "A etapa de ensino é de preenchimento obrigatório.")
    private EtapaEnsinoEnum etapaEnsino;

    @NotBlank(message = "A faixa etária é de preenchimento obrigatório.")
    private String faixaEtaria;

    @NotBlank(message = "O componente curricular é de preenchimento obrigatório.")
    private String componenteCurricular;
}