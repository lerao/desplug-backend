package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.EstadoDTO;
import br.edu.ifpe.afogados.desplugai.dto.MunicipioDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Locale;

@Service
public class LocalidadeService {

    private final String URL_LOCALIDADE = "https://servicodados.ibge.gov.br/api/v1/localidades";
    private final RestClient restClient;

    public LocalidadeService(RestClient.Builder builder) {
        this.restClient = builder
                        .baseUrl(URL_LOCALIDADE)
                        .build();;
    }

    public List<EstadoDTO> listarEstados() {




        List<EstadoDTO> estados = restClient.get()
                .uri("/estados?orderBy=nome")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (estados == null) {
            throw new IllegalStateException(
                    "A API do IBGE não retornou os estados."
            );
        }

        return estados;
    }

    public List<MunicipioDTO> listarMunicipios(String uf) {
        String sigla = uf.trim().toUpperCase(Locale.ROOT);

        EstadoDTO estado = restClient.get()
                .uri("/estados/{id}", uf)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (estado == null) {
            throw new IllegalStateException(
                    "A API do IBGE não retornou o estado."
            );
        }

        List<MunicipioDTO> municipios = restClient.get()
                .uri("/estados/{id}/municipios?orderBy=nome", estado.getId())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (municipios == null) {
            throw new IllegalStateException(
                    "A API do IBGE não retornou os municípios."
            );
        }

        return municipios;
    }
}
