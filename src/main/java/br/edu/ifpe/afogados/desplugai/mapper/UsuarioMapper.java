package br.edu.ifpe.afogados.desplugai.mapper;

import br.edu.ifpe.afogados.desplugai.dto.UsuarioDTO;
import br.edu.ifpe.afogados.desplugai.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    public Usuario toEntity(UsuarioDTO usuarioDTO);

    public UsuarioDTO toDto(Usuario usuario);
}
