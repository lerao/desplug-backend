package br.edu.ifpe.afogados.desplugai.dto;

import br.edu.ifpe.afogados.desplugai.enums.PerfilGlobalEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfilUsuarioDTO {

    private Long id;

    private PerfilGlobalEnum perfil;
}
