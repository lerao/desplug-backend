package br.edu.ifpe.afogados.desplugai.mapper;

import br.edu.ifpe.afogados.desplugai.dto.FeedbackPraticoDTO;
import br.edu.ifpe.afogados.desplugai.entity.FeedbackPratico;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedbackPraticoMapper {

    public FeedbackPratico toEntity(
            FeedbackPraticoDTO feedbackPraticoDTO
    );

    public FeedbackPraticoDTO toDto(
            FeedbackPratico feedbackPratico
    );
}