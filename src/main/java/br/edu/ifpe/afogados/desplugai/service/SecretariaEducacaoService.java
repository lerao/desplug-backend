package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.SecretariaEducacaoDTO;
import br.edu.ifpe.afogados.desplugai.entity.SecretariaEducacao;
import br.edu.ifpe.afogados.desplugai.mapper.SecretariaEducacaoMapper;
import br.edu.ifpe.afogados.desplugai.repository.SecretariaEducacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecretariaEducacaoService {

    private final SecretariaEducacaoRepository secretariaEducacaoRepository;
    private final SecretariaEducacaoMapper secretariaEducacaoMapper;

    public SecretariaEducacaoService(
            SecretariaEducacaoRepository secretariaEducacaoRepository,
            SecretariaEducacaoMapper secretariaEducacaoMapper) {

        this.secretariaEducacaoRepository = secretariaEducacaoRepository;
        this.secretariaEducacaoMapper = secretariaEducacaoMapper;
    }

    public SecretariaEducacaoDTO salvarSecretariaEducacao(
            SecretariaEducacaoDTO secretariaEducacaoDTO) {

        SecretariaEducacao secretariaEducacao =
                secretariaEducacaoMapper.toEntity(secretariaEducacaoDTO);

        SecretariaEducacao secretariaEducacaoSalva =
                secretariaEducacaoRepository.save(secretariaEducacao);

        return secretariaEducacaoMapper.toDto(secretariaEducacaoSalva);
    }

    public List<SecretariaEducacaoDTO> listarSecretariasEducacao() {

        List<SecretariaEducacao> listaDeSecretarias =
                secretariaEducacaoRepository.findAll();

        return listaDeSecretarias
                .stream()
                .map(secretariaEducacaoMapper::toDto)
                .toList();
    }

    public SecretariaEducacaoDTO buscarSecretariaEducacao(Long id) {

        SecretariaEducacao secretariaEducacao =
                secretariaEducacaoRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Secretaria de educação não encontrada"));

        return secretariaEducacaoMapper.toDto(secretariaEducacao);
    }
}

