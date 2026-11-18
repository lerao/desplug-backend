package br.edu.ifpe.afogados.desplugai.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@AllArgsConstructor
@JsonPropertyOrder({"nome", "descricao", "versao", "documentacao", "status"})
public class ApiInfoDTO {
    private String nome;
    private String descricao;
    private String versao;
    private String documentacao;
    private String status;
}
