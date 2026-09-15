package br.edu.ifpe.afogados.desplugai.mapper;

import br.edu.ifpe.afogados.desplugai.dto.HabilidadeBnccDTO;
import br.edu.ifpe.afogados.desplugai.entity.HabilidadeBncc;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HabilidadeBnccMapper {

    public HabilidadeBncc toEntity(HabilidadeBnccDTO habilidadeBnccDTO);

    public HabilidadeBnccDTO toDto(HabilidadeBncc habilidadeBncc);
}