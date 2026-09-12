package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.PlanoAulaDTO;
import br.edu.ifpe.afogados.desplugai.entity.PlanoAula;
import br.edu.ifpe.afogados.desplugai.enums.StatusPublicacaoEnum;
import br.edu.ifpe.afogados.desplugai.mapper.PlanoAulaMapper;
import br.edu.ifpe.afogados.desplugai.repository.PlanoAulaRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class PlanoAulaService {

    private final PlanoAulaRepository planoAulaRepository;
    private final PlanoAulaMapper planoAulaMapper;

    public PlanoAulaService(PlanoAulaRepository planoAulaRepository, PlanoAulaMapper planoAulaMapper) {
        this.planoAulaRepository = planoAulaRepository;
        this.planoAulaMapper = planoAulaMapper;
    }

    public PlanoAulaDTO salvarPlanoAula(PlanoAulaDTO planoAulaDTO) {
        PlanoAula planoAula = planoAulaMapper.toEntity(planoAulaDTO);
        planoAula.setDataCriacao(Timestamp.from(Instant.now()));
        planoAula.setStatusPublicacao(StatusPublicacaoEnum.PENDENTE_MODERACAO);
        PlanoAula planoAulaSalvo = planoAulaRepository.save(planoAula);
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
        planoAulaCadastrado.setIdPlanoOrigem(planoAulaDTO.getIdPlanoOrigem());
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
}
