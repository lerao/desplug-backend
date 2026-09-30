package br.edu.ifpe.afogados.desplugai.dto;

import br.edu.ifpe.afogados.desplugai.enums.SecretariaEducacaoEnum;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({
        "id",
        "nome",
        "tipo",
        "uf",
        "municipio",
        "dominioOficial",
        "ativo"
})
@Getter
@Setter
public class SecretariaEducacaoDTO {

    private Long id;

    @NotBlank(message = "O nome da secretaria de educação é de preenchimento obrigatório.")
    private String nome;

    @NotNull(message = "O tipo da secretaria de educação é de preenchimento obrigatório.")
    private SecretariaEducacaoEnum tipo;

    @NotBlank(message = "A UF é de preenchimento obrigatório.")
    private String uf;

    @NotBlank(message = "O município é de preenchimento obrigatório.")
    private String municipio;

    @NotBlank(message = "O domínio oficial é de preenchimento obrigatório.")
    private String dominioOficial;

    @NotNull(message = "O campo ativo é de preenchimento obrigatório.")
    private Boolean ativo;
}