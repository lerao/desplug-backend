package br.edu.ifpe.afogados.desplugai.dto;

import br.edu.ifpe.afogados.desplugai.entity.Usuario;
import br.edu.ifpe.afogados.desplugai.enums.PerfilGlobalEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SessaoUsuarioDTO {

    private long id;

    private UsuarioDTO usuario;

    private String token;

    private PerfilGlobalEnum perfilAtivo;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataExpiracao;
}
