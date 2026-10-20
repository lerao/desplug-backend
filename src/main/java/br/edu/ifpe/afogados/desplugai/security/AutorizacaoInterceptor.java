
package br.edu.ifpe.afogados.desplugai.security;

import br.edu.ifpe.afogados.desplugai.entity.SessaoUsuario;
import br.edu.ifpe.afogados.desplugai.enums.PerfilGlobalEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class AutorizacaoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws IOException {

        if (isRotaLiberada(request)) {
            return true;
        }

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        PerfisPermitidos permissao =
                AnnotatedElementUtils.findMergedAnnotation(
                        handlerMethod.getMethod(),
                        PerfisPermitidos.class
                );

        if (permissao == null) {
            permissao = AnnotatedElementUtils.findMergedAnnotation(
                    handlerMethod.getBeanType(),
                    PerfisPermitidos.class
            );
        }

        // Sem anotação de perfil, não há restrição adicional
        // neste interceptor. A autenticação continua obrigatória.
        if (permissao == null) {
            return true;
        }

        Object atributo = request.getAttribute(
                AuthInterceptor.SESSAO_ATTRIBUTE
        );

        if (!(atributo instanceof SessaoUsuario sessao)) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Sessão não autenticada"
            );
            return false;
        }

        PerfilGlobalEnum perfilAtivo = sessao.getPerfilAtivo();

        boolean autorizado = Arrays.stream(permissao.value())
                .anyMatch(perfil -> perfil == perfilAtivo);

        if (!autorizado) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Seu perfil não possui permissão para esta operação"
            );
            return false;
        }

        return true;
    }

    private boolean isRotaLiberada(HttpServletRequest request) {
        String path = request.getRequestURI()
                .substring(request.getContextPath().length());

        return path.equals("/usuario") && "POST".equalsIgnoreCase(request.getMethod());
    }
}
