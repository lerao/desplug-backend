package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.*;
import br.edu.ifpe.afogados.desplugai.entity.*;
import br.edu.ifpe.afogados.desplugai.enums.StatusPublicacaoEnum;
import br.edu.ifpe.afogados.desplugai.mapper.ContextoAdaptacaoIAMapper;
import br.edu.ifpe.afogados.desplugai.mapper.HabilidadeBnccMapper;
import br.edu.ifpe.afogados.desplugai.mapper.PlanoAulaMapper;
import br.edu.ifpe.afogados.desplugai.repository.ContextoAdaptacaoIARepository;
import br.edu.ifpe.afogados.desplugai.repository.HabilidadeBnccRepository;
import br.edu.ifpe.afogados.desplugai.repository.PlanoAulaRepository;
import br.edu.ifpe.afogados.desplugai.repository.UsuarioRepository;
import br.edu.ifpe.afogados.desplugai.service.ia.GeminiService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContextoAdaptacaoIAService {

    private final ContextoAdaptacaoIARepository contextoAdaptacaoIARepository;
    private final ContextoAdaptacaoIAMapper contextoAdaptacaoIAMapper;
    private final PlanoAulaMapper planoAulaMapper;
    private final HabilidadeBnccMapper habilidadeBnccMapper;
    private final UsuarioRepository usuarioRepository;
    private final PlanoAulaRepository planoAulaRepository;
    private final HabilidadeBnccRepository habilidadeBnccRepository;

    // Service que gera as adaptações
    private final GeminiService geminiService;

    public ContextoAdaptacaoIAService(
            ContextoAdaptacaoIARepository contextoAdaptacaoIARepository,
            ContextoAdaptacaoIAMapper contextoAdaptacaoIAMapper,
            PlanoAulaMapper planoAulaMapper,
            HabilidadeBnccMapper habilidadeBnccMapper,
            UsuarioRepository usuarioRepository,
            PlanoAulaRepository planoAulaRepository,
            HabilidadeBnccRepository habilidadeBnccRepository,
            GeminiService geminiService
    ) {
        this.contextoAdaptacaoIARepository = contextoAdaptacaoIARepository;
        this.contextoAdaptacaoIAMapper = contextoAdaptacaoIAMapper;
        this.planoAulaMapper = planoAulaMapper;
        this.habilidadeBnccMapper = habilidadeBnccMapper;
        this.usuarioRepository = usuarioRepository;
        this.planoAulaRepository = planoAulaRepository;
        this.habilidadeBnccRepository = habilidadeBnccRepository;
        this.geminiService = geminiService;
    }

    public PlanoAulaDTO gerarAdaptacaoIA(
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

        PlanoAula planoAulaAdaptado = new PlanoAula();
        planoAulaAdaptado.setTitulo(respostaIA.getTitulo());
        planoAulaAdaptado.setResumo(respostaIA.getResumo());
        planoAulaAdaptado.setTipoAtividade(respostaIA.getTipoAtividade());
        planoAulaAdaptado.setEtapaEnsino(respostaIA.getEtapaEnsino());
        planoAulaAdaptado.setAnosIndicados(respostaIA.getAnosIndicados());
        planoAulaAdaptado.setDuracao(respostaIA.getDuracao());
        planoAulaAdaptado.setComponentesCurriculares(respostaIA.getComponentesCurriculares());
        planoAulaAdaptado.setMateriaisNecessarios(respostaIA.getMateriaisNecessarios());
        planoAulaAdaptado.setMetodologia(respostaIA.getMetodologia());
        planoAulaAdaptado.setCriteriosAvaliacao(respostaIA.getCriteriosAvaliacao());

        if(respostaIA.getHabilidades() != null && !respostaIA.getHabilidades().isEmpty()) {
            List<HabilidadePlano> habilidadesPlanoGerado = new ArrayList<>();
            respostaIA.getHabilidades().forEach(habilidade -> {

                Optional<HabilidadeBncc> habilidadeBncc = habilidadeBnccRepository
                        .findByCodigo(habilidade.getCodigoHabilidade());

                if(habilidadeBncc.isPresent()) {
                    HabilidadePlano habilidadePlanoGerado = new HabilidadePlano();
                    habilidadePlanoGerado.setHabilidade(habilidadeBncc.get());
                    habilidadesPlanoGerado.add(habilidadePlanoGerado);
                }

            });
            planoAulaAdaptado.setHabilidadesPlano(habilidadesPlanoGerado);
        } else {
            planoAulaAdaptado.setHabilidadesPlano(planoBase.getHabilidadesPlano());
        }

        planoAulaAdaptado.setImagemCapa(null);
        planoAulaAdaptado.setStatusPublicacao(StatusPublicacaoEnum.PENDENTE_MODERACAO);
        //planoAulaAdaptado.setAutor(); -- USUÁRIO DA SESSÃO
        planoAulaAdaptado.setIsGeradoIA(true);
        planoAulaAdaptado.setIsDerivado(true);
        planoAulaAdaptado.setPlanoOrigem(planoBase);
        planoAulaAdaptado.setVisualizacoes(0);
        planoAulaAdaptado.setDataCriacao(Timestamp.from(Instant.now()));

        // cria a instancia do contexto de adaptação e salva
        ContextoAdaptacaoIA contextoAdaptacaoIA = new ContextoAdaptacaoIA();


        return planoAulaMapper.toDto(planoAulaAdaptado);
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
