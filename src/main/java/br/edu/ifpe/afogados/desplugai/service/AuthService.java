package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.LoginRequestDTO;
import br.edu.ifpe.afogados.desplugai.dto.SessaoUsuarioDTO;
import br.edu.ifpe.afogados.desplugai.entity.SessaoUsuario;
import br.edu.ifpe.afogados.desplugai.entity.Usuario;
import br.edu.ifpe.afogados.desplugai.mapper.SessaoUsuarioMapper;
import br.edu.ifpe.afogados.desplugai.repository.PerfilUsuarioRepository;
import br.edu.ifpe.afogados.desplugai.repository.SessaoUsuarioRepository;
import br.edu.ifpe.afogados.desplugai.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilUsuarioRepository perfilUsuarioRepository;
    private final SessaoUsuarioRepository sessaoUsuarioRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final SessaoUsuarioMapper sessaoUsuarioMapper;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PerfilUsuarioRepository perfilUsuarioRepository,
            SessaoUsuarioRepository sessaoUsuarioRepository,
            TokenService tokenService,
            PasswordEncoder passwordEncoder,
            SessaoUsuarioMapper sessaoUsuarioMapper
    ) {
        this.usuarioRepository = usuarioRepository;
        this.perfilUsuarioRepository = perfilUsuarioRepository;
        this.sessaoUsuarioRepository = sessaoUsuarioRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.sessaoUsuarioMapper = sessaoUsuarioMapper;
    }

    public SessaoUsuarioDTO login(LoginRequestDTO dto) {
        Usuario usuario = usuarioRepository
                .findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("E-mail e/ou senha incorreto(s)."));

        boolean matchSenha = passwordEncoder.matches(dto.getSenha(), usuario.getSenha());

        if (!matchSenha) {
            throw new RuntimeException("E-mail e/ou senha incorreto(s).");
        }

        if(!perfilUsuarioRepository.existsByUsuario_IdAndPerfil(usuario.getId(), dto.getPerfil())) {
            throw new RuntimeException("Acesso não permitido.");
        }

        SessaoUsuario sessaoUsuario = new SessaoUsuario();
        sessaoUsuario.setUsuario(usuario);
        sessaoUsuario.setToken(tokenService.gerarToken());
        sessaoUsuario.setPerfilAtivo(dto.getPerfil());
        sessaoUsuario.setDataCriacao(LocalDateTime.now());
        sessaoUsuario.setDataExpiracao(LocalDateTime.now().plusHours(6));
        sessaoUsuarioRepository.save(sessaoUsuario);

        return sessaoUsuarioMapper.toDto(sessaoUsuario);
    }
}
