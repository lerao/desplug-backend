package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.ProfessorAfiliacaoDTO;
import br.edu.ifpe.afogados.desplugai.entity.ProfessorAfiliacao;
import br.edu.ifpe.afogados.desplugai.enums.StatusAfiliacaoEnum;
import br.edu.ifpe.afogados.desplugai.mapper.ProfessorAfiliacaoMapper;
import br.edu.ifpe.afogados.desplugai.repository.ProfessorAfiliacaoRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
public class ProfessorAfiliacaoService {

    private final ProfessorAfiliacaoRepository professorAfiliacaoRepository;
    private final ProfessorAfiliacaoMapper professorAfiliacaoMapper;

    public ProfessorAfiliacaoService(
            ProfessorAfiliacaoRepository professorAfiliacaoRepository,
            ProfessorAfiliacaoMapper professorAfiliacaoMapper
    ) {
        this.professorAfiliacaoRepository = professorAfiliacaoRepository;
        this.professorAfiliacaoMapper = professorAfiliacaoMapper;
    }

    public ProfessorAfiliacaoDTO salvarProfessorAfiliacao(
            ProfessorAfiliacaoDTO professorAfiliacaoDTO
    ) {
        ProfessorAfiliacao professorAfiliacao = professorAfiliacaoMapper.toEntity(professorAfiliacaoDTO);

        professorAfiliacao.setStatus(StatusAfiliacaoEnum.PENDENTE);

        ProfessorAfiliacao professorAfiliacaoSalvo = professorAfiliacaoRepository.save(professorAfiliacao);

        return professorAfiliacaoMapper.toDto(professorAfiliacaoSalvo);
    }

    public List<ProfessorAfiliacaoDTO> listarProfessorAfiliacoes() {
        List<ProfessorAfiliacao> listaDeProfessorAfiliacoes = professorAfiliacaoRepository.findAll();

        return listaDeProfessorAfiliacoes
                .stream()
                .map(professorAfiliacaoMapper::toDto)
                .toList();
    }

    public ProfessorAfiliacaoDTO buscarProfessorAfiliacao(Long id) {
        ProfessorAfiliacao professorAfiliacao =
                professorAfiliacaoRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Afiliação do professor não encontrada")
                        );

        return professorAfiliacaoMapper.toDto(professorAfiliacao);
    }
}
