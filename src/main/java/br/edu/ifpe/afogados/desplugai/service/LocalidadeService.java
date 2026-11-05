package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.EstadoDTO;
import br.edu.ifpe.afogados.desplugai.dto.LocalidadeSecretariaDTO;
import br.edu.ifpe.afogados.desplugai.dto.MunicipioDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Locale;

@Service
public class LocalidadeService {

    private final String URL_LOCALIDADE = "https://viacep.com.br/ws";
    private final RestClient restClient;

    public LocalidadeService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl(URL_LOCALIDADE).build();
    }


    public LocalidadeSecretariaDTO buscarLocalidade(String cep) {
        LocalidadeSecretariaDTO localidade = restClient.get()
                .uri("/{cep}/json", cep)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (localidade == null) {
            throw new IllegalStateException(
                    "A localidade não foi encontrada."
            );
        }

        return localidade;
    }

}
