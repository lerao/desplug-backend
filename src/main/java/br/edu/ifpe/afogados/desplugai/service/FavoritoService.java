package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.FavoritoDTO;
import br.edu.ifpe.afogados.desplugai.entity.Favorito;
import br.edu.ifpe.afogados.desplugai.mapper.FavoritoMapper;
import br.edu.ifpe.afogados.desplugai.repository.FavoritoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final FavoritoMapper favoritoMapper;

    public FavoritoService(FavoritoRepository favoritoRepository, FavoritoMapper favoritoMapper) {
        this.favoritoRepository = favoritoRepository;
        this.favoritoMapper = favoritoMapper;
    }

    public FavoritoDTO salvarFavorito(FavoritoDTO favoritoDTO) {
        boolean jaFavoritado = favoritoRepository.existsByIdUsuarioAndIdPlano(
                favoritoDTO.getIdUsuario(),
                favoritoDTO.getIdPlano()
        );

        if (jaFavoritado) {
            throw new RuntimeException("Este plano já está favoritado por este usuário");
        }

        Favorito favorito = favoritoMapper.toEntity(favoritoDTO);
        favorito.setDataRegistro(Timestamp.from(Instant.now()));
        Favorito favoritoSalvo = favoritoRepository.save(favorito);
        return favoritoMapper.toDto(favoritoSalvo);
    }

    public List<FavoritoDTO> listarFavoritos() {
        List<Favorito> listaDeFavoritos = favoritoRepository.findAll();

        return listaDeFavoritos
                .stream()
                .map(favoritoMapper::toDto)
                .toList();
    }

    public FavoritoDTO buscarFavorito(Long id) {
        Favorito favorito =
                favoritoRepository
                        .findById(id)
                        .orElseThrow(() -> new RuntimeException("Favorito não encontrado"));

        return favoritoMapper.toDto(favorito);
    }

    public FavoritoDTO atualizarFavorito(Long id, FavoritoDTO favoritoDTO) {
        Favorito favoritoCadastrado = favoritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorito não encontrado"));

        favoritoCadastrado.setIdPlano(favoritoDTO.getIdPlano());
        favoritoCadastrado = favoritoRepository.save(favoritoCadastrado);
        return favoritoMapper.toDto(favoritoCadastrado);
    }

    public ApiResponseDTO deletarFavorito(Long id) {
        Favorito favoritoCadastrado = favoritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorito não encontrado"));

        favoritoRepository.delete(favoritoCadastrado);

        return new ApiResponseDTO(
                HttpStatus.OK.value(),
                "Favorito deletado com sucesso"
        );
    }
}