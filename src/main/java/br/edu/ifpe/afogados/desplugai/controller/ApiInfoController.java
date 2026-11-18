package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ApiInfoDTO;
import br.edu.ifpe.afogados.desplugai.dto.FavoritoDTO;
import br.edu.ifpe.afogados.desplugai.service.ApiInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
@Tag(
        name = "Informações e Status da API",
        description = "Diagnóstico de funcionamento."
)
public class ApiInfoController {

    private final ApiInfoService apiInfoService;

    public ApiInfoController(ApiInfoService apiInfoService) {
        this.apiInfoService = apiInfoService;
    }

    @GetMapping
    @Operation(
            summary = "Obter informações da API",
            description = "Retorna informações sobre o status e funcionamento da API."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Informações da API retornadas com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = ApiInfoDTO.class
                    )
            )
    )
    public ResponseEntity<ApiInfoDTO> obterInformacoes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiInfoService.obterInformacoes());
    }
}
