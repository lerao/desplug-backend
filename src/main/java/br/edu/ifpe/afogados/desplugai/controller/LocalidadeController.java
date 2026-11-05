package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.EstadoDTO;
import br.edu.ifpe.afogados.desplugai.dto.LocalidadeSecretariaDTO;
import br.edu.ifpe.afogados.desplugai.dto.MunicipioDTO;
import br.edu.ifpe.afogados.desplugai.dto.PlanoAulaDTO;
import br.edu.ifpe.afogados.desplugai.service.LocalidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/localidade")
@Tag(
        name = "Localidades",
        description = "Operações de busca relacionadas às localidades (estado, cidade)."
)
public class LocalidadeController {

    private final LocalidadeService localidadeService;

    public LocalidadeController(LocalidadeService localidadeService) {
        this.localidadeService = localidadeService;
    }

    @GetMapping("/{cep}")
    @Operation(
            summary = "Buscar a localidade",
            description = "Retorna a localidade a partir do CEP"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Localidade encontrada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LocalidadeSecretariaDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Localidade não encontrada",
                    content = @Content
            )
    })
    public ResponseEntity<LocalidadeSecretariaDTO> buscarLocalidade(
            @Parameter(
                    description = "CEP da localidade",
                    example = "56812666",
                    required = true
            )
            @PathVariable String cep
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(localidadeService.buscarLocalidade(cep));
    }
}
