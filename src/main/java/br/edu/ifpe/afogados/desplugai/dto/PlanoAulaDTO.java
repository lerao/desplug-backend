package br.edu.ifpe.afogados.desplugai.dto;


import br.edu.ifpe.afogados.desplugai.enums.EtapaEnsinoEnum;
import br.edu.ifpe.afogados.desplugai.enums.StatusPublicacaoEnum;
import br.edu.ifpe.afogados.desplugai.enums.TipoAtividadeEnum;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@JsonPropertyOrder({
        "id",
        "titulo",
        "resumo",
        "tipoAtividade",
        "etapaEnsino",
        "anosIndicados",
        "duracao",
        "componentesCurriculares",
        "materiaisNecessarios",
        "metodologia",
        "imagemCapa",
        "statusPublicacao",
        "idAutor",
        "isGeradoIA",
        "isDerivado",
        "idPlanoOrigem",
        "visualizacoes",
        "dataCriacao",
        "dataAtualizacao"
})
@Getter
@Setter
public class PlanoAulaDTO {

    private Long id;

    @NotBlank(message = "O título do plano de aula é de preenchimento obrigatório.")
    private String titulo;

    @NotBlank(message = "O resumo do plano de aula é de preenchimento obrigatório.")
    private String resumo;

    @NotNull(message = "O tipo de atividade do plano de aula é de preenchimento obrigatório.")
    private TipoAtividadeEnum tipoAtividade;

    @NotNull(message = "A etapa de ensino do plano de aula é de preenchimento obrigatório.")
    private EtapaEnsinoEnum etapaEnsino;

    @NotBlank(message = "Os anos indicados do plano de aula são de preenchimento obrigatório.")
    private String anosIndicados;

    @NotBlank(message = "A duração do plano de aula é de preenchimento obrigatório.")
    private String duracao;

    @NotBlank(message = "Os componentes curriculares do plano de aula são de preenchimento obrigatório.")
    private String componentesCurriculares;

    @NotBlank(message = "Os materiais necessários do plano de aula são de preenchimento obrigatório.")
    private String materiaisNecessarios;

    @NotBlank(message = "A metodologia do plano de aula é de preenchimento obrigatório.")
    private String metodologia;

    @NotBlank(message = "Os critérios de avaliação do plano de aula são de preenchimento obrigatório.")
    private String criteriosAvaliacao;

    private String imagemCapa;

    private StatusPublicacaoEnum statusPublicacao;

    @NotNull(message = "O autor do plano de aula é de preenchimento obrigatório.")
    private String idAutor;

    @NotNull(message = "É necessário informar se o plano é gerado por IA ou não.")
    public Boolean isGeradoIA;

    @NotNull(message = "É necessário informar se o plano é derivado de outro plano ou não.")
    private Boolean isDerivado;

    private String idPlanoOrigem;

    private Integer visualizacoes;

    private Timestamp dataCriacao;

    private Timestamp dataAtualizacao;
}
