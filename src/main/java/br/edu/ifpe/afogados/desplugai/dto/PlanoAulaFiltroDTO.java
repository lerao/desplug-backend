package br.edu.ifpe.afogados.desplugai.dto;

import br.edu.ifpe.afogados.desplugai.enums.EtapaEnsinoEnum;
import br.edu.ifpe.afogados.desplugai.enums.PlanoAulaOrdenacaoEnum;
import br.edu.ifpe.afogados.desplugai.enums.StatusPublicacaoEnum;
import br.edu.ifpe.afogados.desplugai.enums.TipoAtividadeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanoAulaFiltroDTO {
    private String titulo;
    private String resumo;
    private TipoAtividadeEnum tipoAtividade;
    private EtapaEnsinoEnum etapaEnsino;
    private String anosIndicados;
    private String comDuracao;
    private String componenteCurricular;
    private String materiaisNecessarios;
    private String metodologia;
    private String codigoHabilidade;
    private StatusPublicacaoEnum statusPublicacao;
    private Boolean isGeradoIA;
    private PlanoAulaOrdenacaoEnum orderBy;
}
