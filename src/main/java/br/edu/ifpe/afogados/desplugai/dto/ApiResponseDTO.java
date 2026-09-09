package br.edu.ifpe.afogados.desplugai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponseDTO {
    private Integer status;
    private String mensagem;
}
