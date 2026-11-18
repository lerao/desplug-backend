package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.ApiInfoDTO;
import org.springframework.stereotype.Service;

@Service
public class ApiInfoService {

    public ApiInfoDTO obterInformacoes() {
        return new ApiInfoDTO(
            "DesplugAI",
            "Plataforma de criação e adaptação de planos de aula.",
            "1.0.0",
            "/swagger-ui/index.html",
            "ATIVO"
        );
    }
}
