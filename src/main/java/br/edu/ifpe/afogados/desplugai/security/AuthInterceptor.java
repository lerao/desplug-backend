
package br.edu.ifpe.afogados.desplugai.security;

import br.edu.ifpe.afogados.desplugai.entity.SessaoUsuario;
import br.edu.ifpe.afogados.desplugai.repository.SessaoUsuarioRepository;
import br.edu.ifpe.afogados.desplugai.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    public static final String SESSAO_ATTRIBUTE = "sessaoUsuario";

    private final SessaoUsuarioRepository sessaoUsuarioRepository;
    private final TokenService tokenService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization == null
                || !authorization.startsWith("Bearer ")
                || authorization.length() <= 7) {

            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Token de autenticação não informado"
            );
            return false;
        }

        String token = authorization.substring(7).trim();

        if (token.isEmpty()) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Token de autenticação inválido"
            );
            return false;
        }

        String tokenHash = tokenService.gerarHash(token);

        SessaoUsuario sessao = sessaoUsuarioRepository
                .findByToken(tokenHash)
                .orElse(null);

        if (sessao == null) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Sessão inválida ou inexistente"
            );
            return false;
        }

        if (!sessao.getDataExpiracao().isAfter(LocalDateTime.now())) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Sessão expirada"
            );
            return false;
        }

        request.setAttribute(SESSAO_ATTRIBUTE, sessao);

        return true;
    }
}
