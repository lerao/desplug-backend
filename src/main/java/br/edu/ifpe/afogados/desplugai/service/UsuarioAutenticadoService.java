package br.edu.ifpe.afogados.desplugai.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import br.edu.ifpe.afogados.desplugai.enums.PerfilGlobalEnum;

@Service
public class UsuarioAutenticadoService {

    private JwtAuthenticationToken getAutenticacao() {
        var autenticacao = SecurityContextHolder.getContext()
                .getAuthentication();

        if (!(autenticacao instanceof JwtAuthenticationToken jwtAuth)) {
            throw new IllegalStateException(
                    "Não há um usuário autenticado por JWT."
            );
        }

        return jwtAuth;
    }

    public Long getIdUsuario() {
        return Long.valueOf(getAutenticacao().getToken().getSubject());
    }

    public PerfilGlobalEnum getPerfilAtivo() {
        String perfil = getAutenticacao()
                .getToken()
                .getClaimAsString("perfil");

        return PerfilGlobalEnum.valueOf(perfil);
    }

    public boolean isAdmin() {
        return getPerfilAtivo() == PerfilGlobalEnum.ROLE_ADMIN;
    }
}