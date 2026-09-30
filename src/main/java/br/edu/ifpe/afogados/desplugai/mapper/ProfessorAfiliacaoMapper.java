package br.edu.ifpe.afogados.desplugai.mapper;

import br.edu.ifpe.afogados.desplugai.dto.ProfessorAfiliacaoDTO;
import br.edu.ifpe.afogados.desplugai.entity.ProfessorAfiliacao;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = { UsuarioMapper.class, SecretariaEducacaoMapper.class }
)
public interface ProfessorAfiliacaoMapper {

    public ProfessorAfiliacao toEntity(ProfessorAfiliacaoDTO professorAfiliacaoDTO);

    public ProfessorAfiliacaoDTO toDto(ProfessorAfiliacao professorAfiliacao);
}
