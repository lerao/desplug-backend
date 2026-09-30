package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.ProfessorAfiliacaoDTO;
import br.edu.ifpe.afogados.desplugai.entity.ProfessorAfiliacao;
import br.edu.ifpe.afogados.desplugai.entity.SecretariaEducacao;
import br.edu.ifpe.afogados.desplugai.entity.Usuario;
import br.edu.ifpe.afogados.desplugai.enums.StatusAfiliacaoEnum;
import br.edu.ifpe.afogados.desplugai.mapper.ProfessorAfiliacaoMapper;
import br.edu.ifpe.afogados.desplugai.repository.ProfessorAfiliacaoRepository;
import br.edu.ifpe.afogados.desplugai.repository.SecretariaEducacaoRepository;
import br.edu.ifpe.afogados.desplugai.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorAfiliacaoService {

    private final ProfessorAfiliacaoRepository professorAfiliacaoRepository;
    private final ProfessorAfiliacaoMapper professorAfiliacaoMapper;
    private final UsuarioRepository usuarioRepository;
    private final SecretariaEducacaoRepository secretariaEducacaoRepository;

    public ProfessorAfiliacaoService(
            ProfessorAfiliacaoRepository professorAfiliacaoRepository,
            ProfessorAfiliacaoMapper professorAfiliacaoMapper,
            UsuarioRepository usuarioRepository,
            SecretariaEducacaoRepository secretariaEducacaoRepository
    ) {
        this.professorAfiliacaoRepository = professorAfiliacaoRepository;
        this.professorAfiliacaoMapper = professorAfiliacaoMapper;
        this.usuarioRepository = usuarioRepository;
        this.secretariaEducacaoRepository = secretariaEducacaoRepository;
    }

    public ProfessorAfiliacaoDTO salvarProfessorAfiliacao(
            ProfessorAfiliacaoDTO professorAfiliacaoDTO
    ) {
        Usuario professor = usuarioRepository
                .findById(professorAfiliacaoDTO.getProfessor().getId())
                .orElseThrow(() ->
                        new RuntimeException("Professor não encontrado")
                );

        SecretariaEducacao secretaria = secretariaEducacaoRepository
                .findById(professorAfiliacaoDTO.getSecretaria().getId())
                .orElseThrow(() ->
                        new RuntimeException("Secretaria de educação não encontrada")
                );

        ProfessorAfiliacao professorAfiliacao =
                professorAfiliacaoMapper.toEntity(professorAfiliacaoDTO);

        professorAfiliacao.setProfessor(professor);
        professorAfiliacao.setSecretaria(secretaria);

        professorAfiliacao.setStatus(StatusAfiliacaoEnum.PENDENTE);

        ProfessorAfiliacao professorAfiliacaoSalvo =
                professorAfiliacaoRepository.save(professorAfiliacao);

        return professorAfiliacaoMapper.toDto(professorAfiliacaoSalvo);
    }

    public List<ProfessorAfiliacaoDTO> listarProfessorAfiliacoes() {
        List<ProfessorAfiliacao> listaDeProfessoresAfiliacoes =
                professorAfiliacaoRepository.findAll();

        return listaDeProfessoresAfiliacoes
                .stream()
                .map(professorAfiliacaoMapper::toDto)
                .toList();
    }

    public ProfessorAfiliacaoDTO buscarProfessorAfiliacao(Long id) {
        ProfessorAfiliacao professorAfiliacao =
                professorAfiliacaoRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Afiliacao do professor não encontrada"
                                )
                        );

        return professorAfiliacaoMapper.toDto(professorAfiliacao);
    }

    public ProfessorAfiliacaoDTO atualizarProfessorAfiliacao(
            Long id,
            ProfessorAfiliacaoDTO professorAfiliacaoDTO
    ) {
        ProfessorAfiliacao professorAfiliacaoCadastrado =
                professorAfiliacaoRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Afiliacao do professor não encontrada"
                                )
                        );

        Usuario professor = usuarioRepository
                .findById(professorAfiliacaoDTO.getProfessor().getId())
                .orElseThrow(() ->
                        new RuntimeException("Professor não encontrado")
                );

        SecretariaEducacao secretaria = secretariaEducacaoRepository
                .findById(professorAfiliacaoDTO.getSecretaria().getId())
                .orElseThrow(() ->
                        new RuntimeException("Secretaria de educação não encontrada")
                );

        professorAfiliacaoCadastrado.setProfessor(professor);
        professorAfiliacaoCadastrado.setSecretaria(secretaria);

        professorAfiliacaoCadastrado.setStatus(
                professorAfiliacaoDTO.getStatus()
        );

        professorAfiliacaoCadastrado.setDataVinculacao(
                professorAfiliacaoDTO.getDataVinculacao()
        );

        professorAfiliacaoCadastrado =
                professorAfiliacaoRepository.save(professorAfiliacaoCadastrado);

        return professorAfiliacaoMapper.toDto(professorAfiliacaoCadastrado);
    }

    public ApiResponseDTO deletarProfessorAfiliacao(Long id) {
        ProfessorAfiliacao professorAfiliacaoCadastrado =
                professorAfiliacaoRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Afiliacao do professor não encontrada"
                                )
                        );

        professorAfiliacaoRepository.delete(professorAfiliacaoCadastrado);

        return new ApiResponseDTO(
                HttpStatus.OK.value(),
                "Afiliacao do professor deletada com sucesso"
        );
    }
}
