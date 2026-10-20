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

    private static final String[] ROTAS_PUBLICAS = {
            "/auth/**",

            // Swagger UI
            "/swagger",
            "/swagger/",
            "/swagger-ui.html",
            "/swagger-ui/**",

            // Documentação OpenAPI
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/api-docs",
            "/api-docs/**",
            "/api-docs.yaml",

            // Recursos auxiliares do Swagger
            "/swagger-resources/**",
            "/configuration/**",
            "/webjars/**"
    };

    private final AuthInterceptor authInterceptor;
    private final AutorizacaoInterceptor autorizacaoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(ROTAS_PUBLICAS)
                .order(1);

        registry.addInterceptor(autorizacaoInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(ROTAS_PUBLICAS)
                .order(2);
    }
}
