package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.ContextoAdaptacaoIADTO;
import br.edu.ifpe.afogados.desplugai.entity.ContextoAdaptacaoIA;
import br.edu.ifpe.afogados.desplugai.mapper.ContextoAdaptacaoIAMapper;
import br.edu.ifpe.afogados.desplugai.repository.ContextoAdaptacaoIARepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContextoAdaptacaoIAService {

    private final ContextoAdaptacaoIARepository contextoAdaptacaoIARepository;
    private final ContextoAdaptacaoIAMapper contextoAdaptacaoIAMapper;

    public ContextoAdaptacaoIAService(
            ContextoAdaptacaoIARepository contextoAdaptacaoIARepository,
            ContextoAdaptacaoIAMapper contextoAdaptacaoIAMapper
    ) {
        this.contextoAdaptacaoIARepository = contextoAdaptacaoIARepository;
        this.contextoAdaptacaoIAMapper = contextoAdaptacaoIAMapper;
    }

    public ContextoAdaptacaoIADTO salvarContextoAdaptacaoIA(
            ContextoAdaptacaoIADTO contextoAdaptacaoIADTO
    ) {
        ContextoAdaptacaoIA contextoAdaptacaoIA =
                contextoAdaptacaoIAMapper.toEntity(contextoAdaptacaoIADTO);

        ContextoAdaptacaoIA contextoAdaptacaoIASalvo =
                contextoAdaptacaoIARepository.save(contextoAdaptacaoIA);

        return contextoAdaptacaoIAMapper.toDto(contextoAdaptacaoIASalvo);
    }

    public List<ContextoAdaptacaoIADTO> listarContextosAdaptacaoIA() {
        List<ContextoAdaptacaoIA> listaDeContextosAdaptacaoIA =
                contextoAdaptacaoIARepository.findAll();

        return listaDeContextosAdaptacaoIA
                .stream()
                .map(contextoAdaptacaoIAMapper::toDto)
                .toList();
    }

    public ContextoAdaptacaoIADTO buscarContextoAdaptacaoIA(Long id) {
        ContextoAdaptacaoIA contextoAdaptacaoIA =
                contextoAdaptacaoIARepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Contexto de adaptação IA não encontrado")
                        );

        return contextoAdaptacaoIAMapper.toDto(contextoAdaptacaoIA);
    }

    public ContextoAdaptacaoIADTO atualizarContextoAdaptacaoIA(
            Long id,
            ContextoAdaptacaoIADTO contextoAdaptacaoIADTO
    ) {
        ContextoAdaptacaoIA contextoAdaptacaoIACadastrado =
                contextoAdaptacaoIARepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Contexto de adaptação IA não encontrado")
                        );

        contextoAdaptacaoIACadastrado.setIdProfessor(
                contextoAdaptacaoIADTO.getIdProfessor()
        );

        contextoAdaptacaoIACadastrado.setIdPlanoBase(
                contextoAdaptacaoIADTO.getIdPlanoBase()
        );

        contextoAdaptacaoIACadastrado.setIdPlanoGerado(
                contextoAdaptacaoIADTO.getIdPlanoGerado()
        );

        contextoAdaptacaoIACadastrado.setMateriaisDisponiveis(
                contextoAdaptacaoIADTO.getMateriaisDisponiveis()
        );

        contextoAdaptacaoIACadastrado.setPerfilTurma(
                contextoAdaptacaoIADTO.getPerfilTurma()
        );

        contextoAdaptacaoIACadastrado.setHabilidadeFoco(
                contextoAdaptacaoIADTO.getHabilidadeFoco()
        );

        contextoAdaptacaoIACadastrado.setObservacoesInstrucoes(
                contextoAdaptacaoIADTO.getObservacoesInstrucoes()
        );

        contextoAdaptacaoIACadastrado.setRespostaIA(
                contextoAdaptacaoIADTO.getRespostaIA()
        );

        contextoAdaptacaoIACadastrado.setTokensConsumidos(
                contextoAdaptacaoIADTO.getTokensConsumidos()
        );

        contextoAdaptacaoIACadastrado.setDataInteracao(
                contextoAdaptacaoIADTO.getDataInteracao()
        );

        contextoAdaptacaoIACadastrado =
                contextoAdaptacaoIARepository.save(contextoAdaptacaoIACadastrado);

        return contextoAdaptacaoIAMapper.toDto(contextoAdaptacaoIACadastrado);
    }

    public ApiResponseDTO deletarContextoAdaptacaoIA(Long id) {
        ContextoAdaptacaoIA contextoAdaptacaoIACadastrado =
                contextoAdaptacaoIARepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Contexto de adaptação IA não encontrado")
                        );

        contextoAdaptacaoIARepository.delete(contextoAdaptacaoIACadastrado);

        return new ApiResponseDTO(
                HttpStatus.OK.value(),
                "Contexto de adaptação IA deletado com sucesso"
        );
    }
}
