package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.SecretariaEducacaoDTO;
import br.edu.ifpe.afogados.desplugai.entity.SecretariaEducacao;
import br.edu.ifpe.afogados.desplugai.mapper.SecretariaEducacaoMapper;
import br.edu.ifpe.afogados.desplugai.repository.SecretariaEducacaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecretariaEducacaoService {

    private final SecretariaEducacaoRepository secretariaEducacaoRepository;
    private final SecretariaEducacaoMapper secretariaEducacaoMapper;

    public SecretariaEducacaoService(
            SecretariaEducacaoRepository secretariaEducacaoRepository,
            SecretariaEducacaoMapper secretariaEducacaoMapper
    ) {
        this.secretariaEducacaoRepository = secretariaEducacaoRepository;
        this.secretariaEducacaoMapper = secretariaEducacaoMapper;
    }

    public SecretariaEducacaoDTO salvarSecretariaEducacao(
            SecretariaEducacaoDTO secretariaEducacaoDTO
    ) {
        SecretariaEducacao secretariaEducacao =
                secretariaEducacaoMapper.toEntity(secretariaEducacaoDTO);

        secretariaEducacao = secretariaEducacaoRepository.save(secretariaEducacao);

        return secretariaEducacaoMapper.toDto(secretariaEducacao);
    }

    public List<SecretariaEducacaoDTO> listarSecretariasEducacao() {
        return secretariaEducacaoRepository.findAll()
                .stream()
                .map(secretariaEducacaoMapper::toDto)
                .toList();
    }

    public SecretariaEducacaoDTO buscarSecretariaEducacao(Long id) {
        SecretariaEducacao secretariaEducacao =
                secretariaEducacaoRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Secretaria de educação não encontrada"
                                )
                        );

        return secretariaEducacaoMapper.toDto(secretariaEducacao);
    }

    public SecretariaEducacaoDTO atualizarSecretariaEducacao(
            Long id,
            SecretariaEducacaoDTO secretariaEducacaoDTO
    ) {
        SecretariaEducacao secretariaCadastrada =
                secretariaEducacaoRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Secretaria de educação não encontrada"
                                )
                        );

        secretariaCadastrada.setNome(secretariaEducacaoDTO.getNome());
        secretariaCadastrada.setTipo(secretariaEducacaoDTO.getTipo());
        secretariaCadastrada.setUf(secretariaEducacaoDTO.getUf());
        secretariaCadastrada.setMunicipio(secretariaEducacaoDTO.getMunicipio());
        secretariaCadastrada.setDominioOficial(
                secretariaEducacaoDTO.getDominioOficial()
        );
        secretariaCadastrada.setAtivo(secretariaEducacaoDTO.getAtivo());

        secretariaCadastrada =
                secretariaEducacaoRepository.save(secretariaCadastrada);

        return secretariaEducacaoMapper.toDto(secretariaCadastrada);
    }

    public ApiResponseDTO deletarSecretariaEducacao(Long id) {
        SecretariaEducacao secretariaCadastrada =
                secretariaEducacaoRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Secretaria de educação não encontrada"
                                )
                        );

        secretariaEducacaoRepository.delete(secretariaCadastrada);

        return new ApiResponseDTO(
                HttpStatus.OK.value(),
                "Secretaria de educação deletada com sucesso"
        );
    }
}