package br.edu.ifpe.afogados.desplugai.mapper;

import br.edu.ifpe.afogados.desplugai.dto.ContextoAdaptacaoIADTO;
import br.edu.ifpe.afogados.desplugai.entity.ContextoAdaptacaoIA;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContextoAdaptacaoIAMapper {

    ContextoAdaptacaoIA toEntity(ContextoAdaptacaoIADTO dto);

    ContextoAdaptacaoIADTO toDto(ContextoAdaptacaoIA entity);
}