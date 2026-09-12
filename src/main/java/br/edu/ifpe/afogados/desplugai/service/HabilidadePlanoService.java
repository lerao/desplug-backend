package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.HabilidadePlanoDTO;
import br.edu.ifpe.afogados.desplugai.entity.HabilidadePlano;
import br.edu.ifpe.afogados.desplugai.mapper.HabilidadePlanoMapper;
import br.edu.ifpe.afogados.desplugai.repository.HabilidadePlanoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabilidadePlanoService {

    private final HabilidadePlanoRepository habilidadePlanoRepository;
    private final HabilidadePlanoMapper habilidadePlanoMapper;

    public HabilidadePlanoService(
            HabilidadePlanoRepository habilidadePlanoRepository,
            HabilidadePlanoMapper habilidadePlanoMapper
    ) {
        this.habilidadePlanoRepository = habilidadePlanoRepository;
        this.habilidadePlanoMapper = habilidadePlanoMapper;
    }

    public HabilidadePlanoDTO salvarHabilidadePlano(
            HabilidadePlanoDTO habilidadePlanoDTO
    ) {
        HabilidadePlano habilidadePlano =
                habilidadePlanoMapper.toEntity(habilidadePlanoDTO);

        HabilidadePlano habilidadePlanoSalvo =
                habilidadePlanoRepository.save(habilidadePlano);

        return habilidadePlanoMapper.toDto(habilidadePlanoSalvo);
    }

    public List<HabilidadePlanoDTO> listarHabilidadesPlano() {
        List<HabilidadePlano> listaDeHabilidadesPlano =
                habilidadePlanoRepository.findAll();

        return listaDeHabilidadesPlano
                .stream()
                .map(habilidadePlanoMapper::toDto)
                .toList();
    }

    public HabilidadePlanoDTO buscarHabilidadePlano(Long id) {
        HabilidadePlano habilidadePlano =
                habilidadePlanoRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Habilidade do plano não encontrada")
                        );

        return habilidadePlanoMapper.toDto(habilidadePlano);
    }

    public HabilidadePlanoDTO atualizarHabilidadePlano(
            Long id,
            HabilidadePlanoDTO habilidadePlanoDTO
    ) {
        HabilidadePlano habilidadePlanoCadastrado =
                habilidadePlanoRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Habilidade do plano não encontrada")
                        );

        habilidadePlanoCadastrado.setIdPlano(
                habilidadePlanoDTO.getIdPlano()
        );

        habilidadePlanoCadastrado.setIdHabilidade(
                habilidadePlanoDTO.getIdHabilidade()
        );

        habilidadePlanoCadastrado =
                habilidadePlanoRepository.save(habilidadePlanoCadastrado);

        return habilidadePlanoMapper.toDto(habilidadePlanoCadastrado);
    }

    public ApiResponseDTO deletarHabilidadePlano(Long id) {
        HabilidadePlano habilidadePlanoCadastrado =
                habilidadePlanoRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Habilidade do plano não encontrada")
                        );

        habilidadePlanoRepository.delete(habilidadePlanoCadastrado);

        return new ApiResponseDTO(
                HttpStatus.OK.value(),
                "Habilidade do plano deletada com sucesso"
        );
    }
}
