package br.edu.ifpe.afogados.desplugai.mapper;

import br.edu.ifpe.afogados.desplugai.dto.PlanoAulaDTO;
import br.edu.ifpe.afogados.desplugai.entity.PlanoAula;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlanoAulaMapper {

    public PlanoAula toEntity(PlanoAulaDTO planoAulaDTO);

    public PlanoAulaDTO toDto(PlanoAula planoAula);
}
