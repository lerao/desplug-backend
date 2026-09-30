package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.HabilidadeBnccDTO;
import br.edu.ifpe.afogados.desplugai.entity.HabilidadeBncc;
import br.edu.ifpe.afogados.desplugai.mapper.HabilidadeBnccMapper;
import br.edu.ifpe.afogados.desplugai.repository.HabilidadeBnccRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HabilidadeBnccService {

    private final HabilidadeBnccRepository habilidadeBnccRepository;
    private final HabilidadeBnccMapper habilidadeBnccMapper;

    public HabilidadeBnccService(HabilidadeBnccRepository habilidadeBnccRepository, HabilidadeBnccMapper habilidadeBnccMapper) {
        this.habilidadeBnccRepository = habilidadeBnccRepository;
        this.habilidadeBnccMapper = habilidadeBnccMapper;
    }

    public HabilidadeBnccDTO salvarHabilidadeBncc(HabilidadeBnccDTO dto) {
        boolean codigoJaExiste = habilidadeBnccRepository.existsByCodigo(dto.getCodigo());

        if (codigoJaExiste) {
            throw new RuntimeException("Já existe uma habilidade cadastrada com este código");
        }

        HabilidadeBncc habilidadeBncc = habilidadeBnccMapper.toEntity(dto);
        HabilidadeBncc habilidadeSalva = habilidadeBnccRepository.save(habilidadeBncc);
        return habilidadeBnccMapper.toDto(habilidadeSalva);
    }

    public List<HabilidadeBnccDTO> listarHabilidadesBncc() {
        List<HabilidadeBncc> lista = habilidadeBnccRepository.findAll();

        return lista
                .stream()
                .map(habilidadeBnccMapper::toDto)
                .toList();
    }

    public HabilidadeBnccDTO buscarHabilidadeBncc(Long id) {
        HabilidadeBncc habilidadeBncc =
                habilidadeBnccRepository
                        .findById(id)
                        .orElseThrow(() -> new RuntimeException("Habilidade BNCC não encontrada"));

        return habilidadeBnccMapper.toDto(habilidadeBncc);
    }

    public HabilidadeBnccDTO atualizarHabilidadeBncc(Long id, HabilidadeBnccDTO dto) {
        HabilidadeBncc habilidadeCadastrada = habilidadeBnccRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habilidade BNCC não encontrada"));

        habilidadeCadastrada.setCodigo(dto.getCodigo());
        habilidadeCadastrada.setDescricao(dto.getDescricao());
        habilidadeCadastrada.setEixoComputacao(dto.getEixoComputacao());
        habilidadeCadastrada.setEtapaEnsino(dto.getEtapaEnsino());
        habilidadeCadastrada.setFaixaEtaria(dto.getFaixaEtaria());
        habilidadeCadastrada.setComponenteCurricular(dto.getComponenteCurricular());

        habilidadeCadastrada = habilidadeBnccRepository.save(habilidadeCadastrada);
        return habilidadeBnccMapper.toDto(habilidadeCadastrada);
    }

    public ApiResponseDTO deletarHabilidadeBncc(Long id) {
        HabilidadeBncc habilidadeCadastrada = habilidadeBnccRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habilidade BNCC não encontrada"));

        habilidadeBnccRepository.delete(habilidadeCadastrada);

        return new ApiResponseDTO(
                HttpStatus.OK.value(),
                "Habilidade BNCC deletada com sucesso"
        );
    }
}