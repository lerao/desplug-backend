package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.EstadoDTO;
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

    @GetMapping("/estados")
    @Operation(
            summary = "Listar estados",
            description = "Retorna a lista de todos os estados."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Estados listados com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EstadoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Falha ao listar os estados.",
                    content = @Content
            )
    })
    public ResponseEntity<List<EstadoDTO>> listarEstados() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(localidadeService.listarEstados());
    }

    @GetMapping("/{uf}/municipios")
    @Operation(
            summary = "Listar municípios de um estado",
            description = "Retorna a lista de todos os municípios de um estado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Municípios listados com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MunicipioDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Falha ao listar os municípios.",
                    content = @Content
            )
    })
    public ResponseEntity<List<MunicipioDTO>> listarMunicipiosEstado(
            @Parameter(
                    description = "Sigla do estado",
                    example = "BA, PE, RJ",
                    required = true
            )
            @PathVariable String uf
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(localidadeService.listarMunicipios(uf));
    }
}
