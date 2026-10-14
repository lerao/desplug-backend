
package br.edu.ifpe.afogados.desplugai.utils;

import br.edu.ifpe.afogados.desplugai.entity.SessaoUsuario;
import br.edu.ifpe.afogados.desplugai.security.AuthInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class SessaoUtils {

    public SessaoUsuario getSessaoAtual() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            throw new IllegalStateException(
                    "Não existe uma requisição HTTP ativa."
            );
        }

        HttpServletRequest request = attributes.getRequest();

        SessaoUsuario sessao = (SessaoUsuario) request.getAttribute(
                AuthInterceptor.SESSAO_ATTRIBUTE
        );

        if (sessao == null) {
            throw new IllegalStateException(
                    "Sessão do usuário não encontrada na requisição."
            );
        }

        return sessao;
    }
}
