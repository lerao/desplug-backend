package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.PerfilUsuarioDTO;
import br.edu.ifpe.afogados.desplugai.dto.UsuarioDTO;
import br.edu.ifpe.afogados.desplugai.entity.PerfilUsuario;
import br.edu.ifpe.afogados.desplugai.entity.ProfessorAfiliacao;
import br.edu.ifpe.afogados.desplugai.entity.SessaoUsuario;
import br.edu.ifpe.afogados.desplugai.entity.Usuario;
import br.edu.ifpe.afogados.desplugai.enums.PerfilGlobalEnum;
import br.edu.ifpe.afogados.desplugai.enums.StatusAfiliacaoEnum;
import br.edu.ifpe.afogados.desplugai.enums.StatusHomologacaoEnum;
import br.edu.ifpe.afogados.desplugai.mapper.UsuarioMapper;
import br.edu.ifpe.afogados.desplugai.repository.PerfilUsuarioRepository;
import br.edu.ifpe.afogados.desplugai.repository.ProfessorAfiliacaoRepository;
import br.edu.ifpe.afogados.desplugai.repository.UsuarioRepository;
import br.edu.ifpe.afogados.desplugai.utils.SessaoUtils;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilUsuarioRepository perfilUsuarioRepository;
    private final ProfessorAfiliacaoRepository professorAfiliacaoRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final SessaoUtils sessaoUtils;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PerfilUsuarioRepository perfilUsuarioRepository,
            ProfessorAfiliacaoRepository professorAfiliacaoRepository,
            UsuarioMapper usuarioMapper,
            PasswordEncoder passwordEncoder,
            SessaoUtils sessaoUtils
    ) {
        this.usuarioRepository = usuarioRepository;
        this.perfilUsuarioRepository = perfilUsuarioRepository;
        this.professorAfiliacaoRepository = professorAfiliacaoRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
        this.sessaoUtils = sessaoUtils;
    }

    @Transactional
    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {

        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
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

    public ApiResponseDTO alterarStatusUsuario(Long idUsuario, StatusHomologacaoEnum status) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        SessaoUsuario sessao = sessaoUtils.getSessaoAtual();

        if(!PerfilGlobalEnum.ROLE_ADMIN.equals(sessao.getPerfilAtivo())) {
            // perfil de secretaria
            Usuario secretaria = sessao.getUsuario();
            ProfessorAfiliacao professorAfiliacao = professorAfiliacaoRepository.findByProfessor_IdAndSecretaria_Id(
                    idUsuario,
                    secretaria.getId()
            ).orElseThrow(() -> new RuntimeException("Professor não afiliado à secretaria"));

            if(StatusHomologacaoEnum.APROVADO.equals(status)) {
                professorAfiliacao.setStatus(StatusAfiliacaoEnum.VINCULADO);
            } else if(StatusHomologacaoEnum.REPROVADO.equals(status)) {
                professorAfiliacao.setStatus(StatusAfiliacaoEnum.DESVINCULADO);
            } else if (StatusHomologacaoEnum.PENDENTE.equals(status)) {
                professorAfiliacao.setStatus(StatusAfiliacaoEnum.PENDENTE);
            }
            professorAfiliacaoRepository.save(professorAfiliacao);

        }

        usuario.setStatusHomologacao(status);
        usuario.setDataHomologacao(Timestamp.from(Instant.now()));
        usuarioRepository.save(usuario);

        return new ApiResponseDTO(
                HttpStatus.OK.value(),
                "Status do usuário alterado alterado para: %s.".formatted(status)
        );
    }
}
