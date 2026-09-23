package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.HabilidadePlanoDTO;
import br.edu.ifpe.afogados.desplugai.dto.PlanoAulaDTO;
import br.edu.ifpe.afogados.desplugai.entity.HabilidadeBncc;
import br.edu.ifpe.afogados.desplugai.entity.HabilidadePlano;
import br.edu.ifpe.afogados.desplugai.entity.PlanoAula;
import br.edu.ifpe.afogados.desplugai.entity.Usuario;
import br.edu.ifpe.afogados.desplugai.enums.StatusPublicacaoEnum;
import br.edu.ifpe.afogados.desplugai.mapper.PlanoAulaMapper;
import br.edu.ifpe.afogados.desplugai.repository.HabilidadeBnccRepository;
import br.edu.ifpe.afogados.desplugai.repository.HabilidadePlanoRepository;
import br.edu.ifpe.afogados.desplugai.repository.PlanoAulaRepository;
import br.edu.ifpe.afogados.desplugai.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanoAulaService {

    private final PlanoAulaRepository planoAulaRepository;
    private final UsuarioRepository usuarioRepository;
    private final HabilidadeBnccRepository habilidadeBnccRepository;
    private final HabilidadePlanoRepository habilidadePlanoRepository;
    private final PlanoAulaMapper planoAulaMapper;


    public PlanoAulaService(PlanoAulaRepository planoAulaRepository, UsuarioRepository usuarioRepository, HabilidadeBnccRepository habilidadeBnccRepository, HabilidadePlanoRepository habilidadePlanoRepository, PlanoAulaMapper planoAulaMapper) {
        this.planoAulaRepository = planoAulaRepository;
        this.usuarioRepository = usuarioRepository;
        this.habilidadeBnccRepository = habilidadeBnccRepository;
        this.habilidadePlanoRepository = habilidadePlanoRepository;
        this.planoAulaMapper = planoAulaMapper;
    }

    @Transactional
    public PlanoAulaDTO salvarPlanoAula(PlanoAulaDTO planoAulaDTO) {
        Usuario autorPlano  = usuarioRepository
                .findById(planoAulaDTO.getAutor().getId())
                .orElseThrow(() -> new RuntimeException("Autor inválido. O Professor não foi encontrado."));

        PlanoAula planoOrigem = null;

        if(planoAulaDTO.getPlanoOrigem() != null) {
            planoOrigem = planoAulaRepository
                    .findById(planoAulaDTO.getPlanoOrigem().getId())
                    .orElseThrow(() -> new RuntimeException("Plano de origem não encontrado."));

        }

        List<HabilidadeBncc> habilidadesBncc = new ArrayList<>();

        planoAulaDTO.getHabilidadesPlano().forEach(habilidade -> {
            if(habilidade.getHabilidade() == null) {
                throw new RuntimeException("Habilidade BNCC inválida.");
            } else {
                HabilidadeBncc habilidadeBncc = habilidadeBnccRepository
                        .findById(habilidade.getHabilidade().getId())
                        .orElseThrow(() -> new RuntimeException("A habilidade não foi encontrada. Id: " + habilidade.getHabilidade().getId()));
                habilidadesBncc.add(habilidadeBncc);
            }
        });



        PlanoAula planoAula = planoAulaMapper.toEntity(planoAulaDTO);
        planoAula.setDataCriacao(Timestamp.from(Instant.now()));
        planoAula.setStatusPublicacao(StatusPublicacaoEnum.PENDENTE_MODERACAO);
        planoAula.setVisualizacoes(0);
        planoAula.setAutor(autorPlano);
        planoAula.setPlanoOrigem(planoOrigem);

        PlanoAula planoAulaSalvo = planoAulaRepository.save(planoAula);

        List<HabilidadePlano> habilidadesPlano = new ArrayList<>();
        habilidadesBncc.forEach(habilidade -> {
            HabilidadePlano habilidadePlanoSalva = new HabilidadePlano();
            habilidadePlanoSalva.setPlano(planoAulaSalvo);
            habilidadePlanoSalva.setHabilidade(habilidade);
            habilidadesPlano.add(habilidadePlanoSalva);
        });

        habilidadePlanoRepository.saveAll(habilidadesPlano);

        return planoAulaMapper.toDto(planoAulaSalvo);
    }

    public List<PlanoAulaDTO> listarPlanosAula() {
        List<PlanoAula> listaPlanosAula = planoAulaRepository.findAll();
        return listaPlanosAula
                .stream()
                .map(planoAulaMapper::toDto)
                .toList();
    }

    public PlanoAulaDTO buscarPlanoAula(Long id) {
        PlanoAula planoAula = planoAulaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Plano de aula não encontrado"));

        return planoAulaMapper.toDto(planoAula);
    }

    public PlanoAulaDTO atualizarPlanoAula(Long id, PlanoAulaDTO planoAulaDTO) {
        PlanoAula planoAulaCadastrado = planoAulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano de aula não encontrado"));

        PlanoAula planoOrigem = null;

        if(planoAulaDTO.getPlanoOrigem() != null) {
            planoOrigem = planoAulaRepository
                    .findById(planoAulaDTO.getPlanoOrigem().getId())
                    .orElseThrow(() -> new RuntimeException("Plano de origem não encontrado."));

        }

        planoAulaCadastrado.getHabilidadesPlano().clear();

        for(HabilidadePlanoDTO habilidadePlanoDTO: planoAulaDTO.getHabilidadesPlano()) {
            if(habilidadePlanoDTO.getHabilidade() == null) {
                throw new RuntimeException("Habilidade BNCC inválida.");
            } else {
                HabilidadeBncc habilidadeBncc = habilidadeBnccRepository
                        .findById(habilidadePlanoDTO.getHabilidade().getId())
                        .orElseThrow(() -> new RuntimeException("A habilidade não foi encontrada. Id: " + habilidadePlanoDTO.getHabilidade().getId()));

                HabilidadePlano habilidadePlano = new HabilidadePlano();
                habilidadePlano.setPlano(planoAulaCadastrado);
                habilidadePlano.setHabilidade(habilidadeBncc);
                planoAulaCadastrado.getHabilidadesPlano().add(habilidadePlano);
            }
        }

        planoAulaCadastrado.setTitulo(planoAulaDTO.getTitulo());
        planoAulaCadastrado.setResumo(planoAulaDTO.getResumo());
        planoAulaCadastrado.setTipoAtividade(planoAulaDTO.getTipoAtividade());
        planoAulaCadastrado.setEtapaEnsino(planoAulaDTO.getEtapaEnsino());
        planoAulaCadastrado.setAnosIndicados(planoAulaDTO.getAnosIndicados());
        planoAulaCadastrado.setDuracao(planoAulaDTO.getDuracao());
        planoAulaCadastrado.setComponentesCurriculares(planoAulaDTO.getComponentesCurriculares());
        planoAulaCadastrado.setMateriaisNecessarios(planoAulaDTO.getMateriaisNecessarios());
        planoAulaCadastrado.setMetodologia(planoAulaDTO.getMetodologia());
        planoAulaCadastrado.setCriteriosAvaliacao(planoAulaDTO.getCriteriosAvaliacao());
        planoAulaCadastrado.setImagemCapa(planoAulaDTO.getImagemCapa());
        planoAulaCadastrado.setStatusPublicacao(StatusPublicacaoEnum.PENDENTE_MODERACAO);
        planoAulaCadastrado.setIsGeradoIA(planoAulaDTO.getIsGeradoIA());
        planoAulaCadastrado.setIsDerivado(planoAulaDTO.getIsDerivado());
        planoAulaCadastrado.setPlanoOrigem(planoOrigem);
        planoAulaCadastrado.setDataAtualizacao(Timestamp.from(Instant.now()));

        planoAulaCadastrado = planoAulaRepository.save(planoAulaCadastrado);
        return planoAulaMapper.toDto(planoAulaCadastrado);
    }

    public ApiResponseDTO deletarPlanoAula(Long id) {
        PlanoAula planoAulaCadastrado = planoAulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano de aula não encontrado"));

        planoAulaRepository.delete(planoAulaCadastrado);

        return new ApiResponseDTO(
                200,
                "Plano de aula deletado com sucesso"
        );
    }


    public List<PlanoAulaDTO> listarPlanosAulaAutor(Long autorId) {
        List<PlanoAula> planosAulaUsuario = planoAulaRepository.findByAutor(autorId);

        return planosAulaUsuario
                .stream()
                .map(planoAulaMapper::toDto)
                .toList();

    }
}
