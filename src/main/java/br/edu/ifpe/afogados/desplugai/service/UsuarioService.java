package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.PerfilUsuarioDTO;
import br.edu.ifpe.afogados.desplugai.dto.UsuarioDTO;
import br.edu.ifpe.afogados.desplugai.entity.PerfilUsuario;
import br.edu.ifpe.afogados.desplugai.entity.Usuario;
import br.edu.ifpe.afogados.desplugai.enums.StatusHomologacaoEnum;
import br.edu.ifpe.afogados.desplugai.mapper.UsuarioMapper;
import br.edu.ifpe.afogados.desplugai.repository.PerfilUsuarioRepository;
import br.edu.ifpe.afogados.desplugai.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilUsuarioRepository perfilUsuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, PerfilUsuarioRepository perfilUsuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.perfilUsuarioRepository = perfilUsuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {

        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario.setDataRegistro(Timestamp.from(Instant.now()));
        usuario.setStatusHomologacao(StatusHomologacaoEnum.PENDENTE);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        List<PerfilUsuario> perfisUsuario = new ArrayList<>();
        usuarioDTO.getPerfisUsuario().forEach(perfil -> {
            PerfilUsuario perfilUsuario = new PerfilUsuario();
            perfilUsuario.setUsuario(usuarioSalvo);
            perfilUsuario.setPerfil(perfil.getPerfil());
            perfisUsuario.add(perfilUsuario);
        });

        perfilUsuarioRepository.saveAll(perfisUsuario);
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

    public UsuarioDTO atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioCadastrado = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if(usuarioDTO.getPerfisUsuario() != null) {
            for(PerfilUsuarioDTO perfil : usuarioDTO.getPerfisUsuario()) {
                if(perfilUsuarioRepository.existsByUsuario_IdAndPerfil(usuarioCadastrado.getId(), perfil.getPerfil())) {
                    throw new RuntimeException("O perfil de %s já é vinculado ao usuário".formatted(perfil.getPerfil().getDescricao()));
                }
            }
        }



        usuarioCadastrado.setNome(usuarioDTO.getNome());
        usuarioCadastrado = usuarioRepository.save(usuarioCadastrado);
        return usuarioMapper.toDto(usuarioCadastrado);
    }

    public ApiResponseDTO deletarUsuario(Long id) {
        Usuario usuarioCadastrado = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioRepository.delete(usuarioCadastrado);

        return new ApiResponseDTO(
                HttpStatus.OK.value(),
                "Usuário deletado com sucesso"
        );
    }
}
