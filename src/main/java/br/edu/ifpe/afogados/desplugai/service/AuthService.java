package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.LoginRequestDTO;
import br.edu.ifpe.afogados.desplugai.dto.SessaoUsuarioDTO;
import br.edu.ifpe.afogados.desplugai.entity.SessaoUsuario;
import br.edu.ifpe.afogados.desplugai.entity.Usuario;
import br.edu.ifpe.afogados.desplugai.mapper.SessaoUsuarioMapper;
import br.edu.ifpe.afogados.desplugai.repository.PerfilUsuarioRepository;
import br.edu.ifpe.afogados.desplugai.repository.SessaoUsuarioRepository;
import br.edu.ifpe.afogados.desplugai.repository.UsuarioRepository;
import br.edu.ifpe.afogados.desplugai.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilUsuarioRepository perfilUsuarioRepository;
    private final SessaoUsuarioRepository sessaoUsuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final SessaoUsuarioMapper sessaoUsuarioMapper;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PerfilUsuarioRepository perfilUsuarioRepository,
            SessaoUsuarioRepository sessaoUsuarioRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            SessaoUsuarioMapper sessaoUsuarioMapper
    ) {
        this.usuarioRepository = usuarioRepository;
        this.perfilUsuarioRepository = perfilUsuarioRepository;
        this.sessaoUsuarioRepository = sessaoUsuarioRepository;
        this.jwtService = jwtService;
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
            throw new RuntimeException("O usuário não possui a credencial necessária.");
        }

        SessaoUsuario sessao = new SessaoUsuario();
        sessao.setUsuario(usuario);
        sessao.setPerfilAtivo(dto.getPerfil());
        sessao.setDataCriacao(LocalDateTime.now());
        sessao.setDataExpiracao(
                LocalDateTime.now().plusMinutes(360)
        );

        sessaoUsuarioRepository.save(sessao);

        SessaoUsuarioDTO sessaoUsuarioDTO = sessaoUsuarioMapper.toDto(sessao);
        sessaoUsuarioDTO.setToken(jwtService.gerarToken(usuario.getId(), sessao.getPerfilAtivo()));

        return sessaoUsuarioDTO;
    }
}
