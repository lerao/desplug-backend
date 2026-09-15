package br.edu.ifpe.afogados.desplugai.dto;


import br.edu.ifpe.afogados.desplugai.enums.PerfilGlobalEnum;
import br.edu.ifpe.afogados.desplugai.enums.StatusHomologacaoEnum;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@JsonPropertyOrder({
        "id",
        "nome",
        "email",
        "senha",
        "perfilGlobal",
        "statusHomologacao",
        "dataRegistro",
        "dataHomologacao",
        "usuarioHomologacao"
})
@Getter
@Setter
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "O nome do usuário é de preenchimento obrigatório.")
    private String nome;

    @NotBlank(message = "O e-mail do usuário é de preenchimento obrigatório.")
    @Email(message = "O e-mail do usuário deve ser válido.")
    private String email;

    @NotBlank(message = "A senha do usuário é de preenchimento obrigatório.")
    private String senha;

    @NotNull(message = "O perfil global do usuário é de preenchimento obrigatório.")
    private PerfilGlobalEnum perfilGlobal;

    private StatusHomologacaoEnum statusHomologacao;

    private Timestamp dataRegistro;

    private Timestamp dataHomologacao;

    private String usuarioHomologacao;
}
