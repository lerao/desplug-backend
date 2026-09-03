package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.UsuarioDTO;
import br.edu.ifpe.afogados.desplugai.entity.Usuario;
import br.edu.ifpe.afogados.desplugai.enums.StatusHomologacaoEnum;
import br.edu.ifpe.afogados.desplugai.mapper.UsuarioMapper;
import br.edu.ifpe.afogados.desplugai.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario.setDataRegistro(Timestamp.from(Instant.now()));
        usuario.setStatusHomologacao(StatusHomologacaoEnum.PENDENTE);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(usuarioSalvo);
    }

    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> listaDeUsuarios = usuarioRepository.findAll();

        return listaDeUsuarios
                .stream()
                .map(usuarioMapper::toDto)
                .toList();
    }

    public UsuarioDTO buscarUsuario(Long id) {
        Usuario usuario =
                usuarioRepository
                        .findById(id)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return usuarioMapper.toDto(usuario);
    }
}
