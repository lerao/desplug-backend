package br.edu.ifpe.afogados.desplugai.mapper;

import br.edu.ifpe.afogados.desplugai.dto.HabilidadePlanoDTO;
import br.edu.ifpe.afogados.desplugai.entity.HabilidadePlano;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HabilidadePlanoMapper {

    public HabilidadePlano toEntity(HabilidadePlanoDTO habilidadePlanoDTO);

    public HabilidadePlanoDTO toDto(HabilidadePlano habilidadePlano);
}
