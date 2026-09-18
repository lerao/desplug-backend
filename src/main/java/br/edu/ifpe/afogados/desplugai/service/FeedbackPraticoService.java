package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.FeedbackPraticoDTO;
import br.edu.ifpe.afogados.desplugai.entity.FeedbackPratico;
import br.edu.ifpe.afogados.desplugai.mapper.FeedbackPraticoMapper;
import br.edu.ifpe.afogados.desplugai.repository.FeedbackPraticoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class FeedbackPraticoService {

    private final FeedbackPraticoRepository feedbackPraticoRepository;
    private final FeedbackPraticoMapper feedbackPraticoMapper;

    public FeedbackPraticoService(
            FeedbackPraticoRepository feedbackPraticoRepository,
            FeedbackPraticoMapper feedbackPraticoMapper
    ) {
        this.feedbackPraticoRepository = feedbackPraticoRepository;
        this.feedbackPraticoMapper = feedbackPraticoMapper;
    }

    public FeedbackPraticoDTO salvarFeedbackPratico(
            FeedbackPraticoDTO feedbackPraticoDTO
    ) {

        FeedbackPratico feedbackPratico =
                feedbackPraticoMapper.toEntity(feedbackPraticoDTO);

        feedbackPratico.setDataRegistro(
                Timestamp.from(Instant.now())
        );

        feedbackPratico =
                feedbackPraticoRepository.save(feedbackPratico);

        return feedbackPraticoMapper.toDto(feedbackPratico);
    }

    public List<FeedbackPraticoDTO> listarFeedbacksPraticos() {

        return feedbackPraticoRepository.findAll()
                .stream()
                .map(feedbackPraticoMapper::toDto)
                .toList();
    }

    public FeedbackPraticoDTO buscarFeedbackPratico(Long id) {

        FeedbackPratico feedbackPratico =
                feedbackPraticoRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Feedback prático não encontrado"
                                )
                        );

        return feedbackPraticoMapper.toDto(feedbackPratico);
    }

    public FeedbackPraticoDTO atualizarFeedbackPratico(
            Long id,
            FeedbackPraticoDTO feedbackPraticoDTO
    ) {

        FeedbackPratico feedbackPraticoCadastrado =
                feedbackPraticoRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Feedback prático não encontrado"
                                )
                        );

        feedbackPraticoCadastrado.setIdPlano(
                feedbackPraticoDTO.getIdPlano()
        );

        feedbackPraticoCadastrado.setIdProfessor(
                feedbackPraticoDTO.getIdProfessor()
        );

        feedbackPraticoCadastrado.setRelatoExperiencia(
                feedbackPraticoDTO.getRelatoExperiencia()
        );

        feedbackPraticoCadastrado.setAvaliacaoGeral(
                feedbackPraticoDTO.getAvaliacaoGeral()
        );

        feedbackPraticoCadastrado.setFotosEvidencias(
                feedbackPraticoDTO.getFotosEvidencias()
        );

        feedbackPraticoCadastrado.setDataAplicacao(
                feedbackPraticoDTO.getDataAplicacao()
        );

        feedbackPraticoCadastrado =
                feedbackPraticoRepository.save(
                        feedbackPraticoCadastrado
                );

        return feedbackPraticoMapper.toDto(
                feedbackPraticoCadastrado
        );
    }

    public ApiResponseDTO deletarFeedbackPratico(Long id) {

        FeedbackPratico feedbackPraticoCadastrado =
                feedbackPraticoRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Feedback prático não encontrado"
                                )
                        );

        feedbackPraticoRepository.delete(
                feedbackPraticoCadastrado
        );

        return new ApiResponseDTO(
                HttpStatus.OK.value(),
                "Feedback prático deletado com sucesso"
        );
    }
}