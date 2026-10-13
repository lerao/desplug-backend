package br.edu.ifpe.afogados.desplugai.mapper;

import br.edu.ifpe.afogados.desplugai.dto.SessaoUsuarioDTO;
import br.edu.ifpe.afogados.desplugai.entity.SessaoUsuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { UsuarioMapper.class })
public interface SessaoUsuarioMapper {

    public SessaoUsuario toEntity(SessaoUsuarioDTO sessaoUsuarioDTO);

    public SessaoUsuarioDTO toDto(SessaoUsuario sessaoUsuario);
}
