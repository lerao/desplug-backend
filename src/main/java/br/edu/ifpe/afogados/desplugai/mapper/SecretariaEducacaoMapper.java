package br.edu.ifpe.afogados.desplugai.mapper;

import br.edu.ifpe.afogados.desplugai.dto.SecretariaEducacaoDTO;
import br.edu.ifpe.afogados.desplugai.entity.SecretariaEducacao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SecretariaEducacaoMapper {

    public SecretariaEducacao toEntity(SecretariaEducacaoDTO secretariaEducacaoDTO);

    public SecretariaEducacaoDTO toDto(SecretariaEducacao secretariaEducacao);
}