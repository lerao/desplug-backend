package br.edu.ifpe.afogados.desplugai.dto;

import br.edu.ifpe.afogados.desplugai.enums.EtapaEnsinoEnum;
import br.edu.ifpe.afogados.desplugai.enums.TipoAtividadeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlanoAulaReportDTO {

    private String nomeInstituicao;
    private String tituloPlano;
    private String nomeProfessor;
    private String dataPlano;
    private String resumoPlano;
    private String tipoAtividade;
    private String etapaEnsino;
    private String anosIndicados;
    private String duracao;
    private String componentesCurriculares;
    private String materiaisNecessarios;
    private String metodologia;
    private String criteriosAvaliacao;
    private List<String> habilidadeBncc;
}
