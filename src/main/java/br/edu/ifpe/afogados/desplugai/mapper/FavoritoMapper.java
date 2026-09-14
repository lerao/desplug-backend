package br.edu.ifpe.afogados.desplugai.mapper;

import br.edu.ifpe.afogados.desplugai.dto.FavoritoDTO;
import br.edu.ifpe.afogados.desplugai.entity.Favorito;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FavoritoMapper {

    public Favorito toEntity(FavoritoDTO favoritoDTO);

    public FavoritoDTO toDto(Favorito favorito);
}