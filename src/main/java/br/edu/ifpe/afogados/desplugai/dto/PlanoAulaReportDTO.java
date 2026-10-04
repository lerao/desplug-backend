package br.edu.ifpe.afogados.desplugai.dto;

import br.edu.ifpe.afogados.desplugai.enums.EtapaEnsinoEnum;
import br.edu.ifpe.afogados.desplugai.enums.TipoAtividadeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlanoAulaReportDTO {

    private String titulo;
    private String resumo;
    private String tipoAtividade;
    private String etapaEnsino;
    private String anosIndicados;
    private String duracao;
}
