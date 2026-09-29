package br.edu.ifpe.afogados.desplugai.mapper;

import br.edu.ifpe.afogados.desplugai.dto.ContextoAdaptacaoIADTO;
import br.edu.ifpe.afogados.desplugai.entity.ContextoAdaptacaoIA;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = { UsuarioMapper.class, PlanoAulaMapper.class }
)
public interface ContextoAdaptacaoIAMapper {

    public ContextoAdaptacaoIA toEntity(ContextoAdaptacaoIADTO dto);

    public ContextoAdaptacaoIADTO toDto(ContextoAdaptacaoIA entity);
}