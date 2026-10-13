package br.edu.ifpe.afogados.desplugai.config;

import br.edu.ifpe.afogados.desplugai.security.AuthInterceptor;
import br.edu.ifpe.afogados.desplugai.security.AutorizacaoInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final AutorizacaoInterceptor autorizacaoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/**")
                .order(1);

        registry.addInterceptor(autorizacaoInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/**")
                .order(2);
    }
}
