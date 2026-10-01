package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.AdaptacaoPlanoInput;
import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.ContextoAdaptacaoIADTO;
import br.edu.ifpe.afogados.desplugai.dto.PlanoAulaIAResponseDTO;
import br.edu.ifpe.afogados.desplugai.entity.ContextoAdaptacaoIA;
import br.edu.ifpe.afogados.desplugai.entity.PlanoAula;
import br.edu.ifpe.afogados.desplugai.entity.Usuario;
import br.edu.ifpe.afogados.desplugai.mapper.ContextoAdaptacaoIAMapper;
import br.edu.ifpe.afogados.desplugai.mapper.PlanoAulaMapper;
import br.edu.ifpe.afogados.desplugai.repository.ContextoAdaptacaoIARepository;
import br.edu.ifpe.afogados.desplugai.repository.PlanoAulaRepository;
import br.edu.ifpe.afogados.desplugai.repository.UsuarioRepository;
import br.edu.ifpe.afogados.desplugai.service.ia.GeminiService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContextoAdaptacaoIAService {

    private final ContextoAdaptacaoIARepository contextoAdaptacaoIARepository;
    private final ContextoAdaptacaoIAMapper contextoAdaptacaoIAMapper;
    private final PlanoAulaMapper planoAulaMapper;
    private final UsuarioRepository usuarioRepository;
    private final PlanoAulaRepository planoAulaRepository;

    // Service que gera as adaptações
    private final GeminiService geminiService;

    public ContextoAdaptacaoIAService(
            ContextoAdaptacaoIARepository contextoAdaptacaoIARepository,
            ContextoAdaptacaoIAMapper contextoAdaptacaoIAMapper,
            PlanoAulaMapper planoAulaMapper,
            UsuarioRepository usuarioRepository,
            PlanoAulaRepository planoAulaRepository,
            GeminiService geminiService
    ) {
        this.contextoAdaptacaoIARepository = contextoAdaptacaoIARepository;
        this.contextoAdaptacaoIAMapper = contextoAdaptacaoIAMapper;
        this.planoAulaMapper = planoAulaMapper;
        this.usuarioRepository = usuarioRepository;
        this.planoAulaRepository = planoAulaRepository;
        this.geminiService = geminiService;
    }

    public PlanoAulaIAResponseDTO gerarAdaptacaoIA(
            Long idPLanoBase,
            AdaptacaoPlanoInput adaptacaoPlanoInput
    ) {

        PlanoAula planoBase = planoAulaRepository
                .findById(idPLanoBase)
                .orElseThrow(() ->
                        new RuntimeException("Plano de aula base não encontrado")
                );

        PlanoAulaIAResponseDTO respostaIA = geminiService.adaptarPlano(
                planoAulaMapper.toDto(planoBase),
                adaptacaoPlanoInput
        );

        return new PlanoAulaIAResponseDTO();
    }

    public ContextoAdaptacaoIADTO salvarContextoAdaptacaoIA(
            ContextoAdaptacaoIADTO contextoAdaptacaoIADTO
    ) {
        Usuario professor = usuarioRepository
                .findById(contextoAdaptacaoIADTO.getIdProfessor())
                .orElseThrow(() ->
                        new RuntimeException("Professor não encontrado")
                );

        PlanoAula planoBase = null;

        if (contextoAdaptacaoIADTO.getIdPlanoBase() != null) {
            planoBase = planoAulaRepository
                    .findById(contextoAdaptacaoIADTO.getIdPlanoBase())
                    .orElseThrow(() ->
                            new RuntimeException("Plano de aula base não encontrado")
                    );
        }

        PlanoAula planoGerado = null;

        if (contextoAdaptacaoIADTO.getPlanoGerado() != null) {
            planoGerado = planoAulaRepository
                    .findById(contextoAdaptacaoIADTO.getPlanoGerado().getId())
                    .orElseThrow(() ->
                            new RuntimeException("Plano de aula gerado não encontrado")
                    );
        }

        ContextoAdaptacaoIA contextoAdaptacaoIA =
                contextoAdaptacaoIAMapper.toEntity(contextoAdaptacaoIADTO);

        contextoAdaptacaoIA.setProfessor(professor);
        contextoAdaptacaoIA.setPlanoBase(planoBase);
        contextoAdaptacaoIA.setPlanoGerado(planoGerado);

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
                                new RuntimeException(
                                        "Contexto de adaptação IA não encontrado"
                                )
                        );

        return contextoAdaptacaoIAMapper.toDto(contextoAdaptacaoIA);
    }

    public ContextoAdaptacaoIADTO atualizarContextoAdaptacaoIA(
            Long id,
            ContextoAdaptacaoIADTO contextoAdaptacaoIADTO
    ) {
        ContextoAdaptacaoIA contextoAdaptacaoIACadastrado =
                contextoAdaptacaoIARepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Contexto de adaptação IA não encontrado"
                                )
                        );

        Usuario professor = usuarioRepository
                .findById(contextoAdaptacaoIADTO.getIdProfessor())
                .orElseThrow(() ->
                        new RuntimeException("Professor não encontrado")
                );

        PlanoAula planoBase = null;

        if (contextoAdaptacaoIADTO.getIdPlanoBase() != null) {
            planoBase = planoAulaRepository
                    .findById(contextoAdaptacaoIADTO.getIdPlanoBase())
                    .orElseThrow(() ->
                            new RuntimeException("Plano de aula base não encontrado")
                    );
        }

        PlanoAula planoGerado = null;

        if (contextoAdaptacaoIADTO.getPlanoGerado() != null) {
            planoGerado = planoAulaRepository
                    .findById(contextoAdaptacaoIADTO.getPlanoGerado().getId())
                    .orElseThrow(() ->
                            new RuntimeException("Plano de aula gerado não encontrado")
                    );
        }

        contextoAdaptacaoIACadastrado.setProfessor(professor);
        contextoAdaptacaoIACadastrado.setPlanoBase(planoBase);
        contextoAdaptacaoIACadastrado.setPlanoGerado(planoGerado);

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
                contextoAdaptacaoIARepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Contexto de adaptação IA não encontrado"
                                )
                        );

        contextoAdaptacaoIARepository.delete(contextoAdaptacaoIACadastrado);

        return new ApiResponseDTO(
                HttpStatus.OK.value(),
                "Contexto de adaptação IA deletado com sucesso"
        );
    }
}
