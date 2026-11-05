package br.edu.ifpe.afogados.desplugai.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalidadeSecretariaDTO {
    private String cep;
    private String logradouro;
    private String localidade;
    private String uf;
    private String estado;
}
