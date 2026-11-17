package br.edu.ifpe.afogados.desplugai.dto;

import br.edu.ifpe.afogados.desplugai.enums.EtapaEnsinoEnum;
import br.edu.ifpe.afogados.desplugai.enums.TipoAtividadeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlanoAulaIAResponseDTO {

    private String titulo;

    private String resumo;

    private TipoAtividadeEnum tipoAtividade;

    private EtapaEnsinoEnum etapaEnsino;

    private String anosIndicados;

    private String duracao;

    private String componentesCurriculares;

    private String materiaisNecessarios;

    private String metodologia;

    private String criteriosAvaliacao;

    private List<HabilidadeIAResponseDTO> habilidades;
}