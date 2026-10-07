package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.PlanoAulaDTO;
import br.edu.ifpe.afogados.desplugai.service.PlanoAulaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plano-aula")
@Tag(
        name = "Planos de Aula",
        description = "Operações relacionadas ao gerenciamento de planos de aula."
)
public class PlanoAulaController {

    private final PlanoAulaService planoAulaService;

    public PlanoAulaController(PlanoAulaService planoAulaService) {
        this.planoAulaService = planoAulaService;
    }

    @PostMapping
    @Operation(
            summary = "Criar plano de aula",
            description = "Cria um novo plano de aula no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Plano de aula criado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlanoAulaDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            )
    })
    public ResponseEntity<PlanoAulaDTO> salvarPlanoAula(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do plano de aula que será criado.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlanoAulaDTO.class)
                    )
            )
            @RequestBody @Valid PlanoAulaDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(planoAulaService.salvarPlanoAula(dto));
    }

    @GetMapping
    @Operation(
            summary = "Listar planos de aula",
            description = "Retorna todos os planos de aula cadastrados no sistema."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Planos de aula retornados com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = PlanoAulaDTO.class
                    )
            )
    )
    public ResponseEntity<List<PlanoAulaDTO>> listarPlanosAula() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(planoAulaService.listarPlanosAula());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar plano de aula por ID",
            description = "Retorna um plano de aula específico a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Plano de aula encontrado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlanoAulaDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Plano de aula não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<PlanoAulaDTO> buscarPlanoAula(
            @Parameter(
                    description = "Identificador do plano de aula",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(planoAulaService.buscarPlanoAula(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar plano de aula",
            description = "Atualiza os dados de um plano de aula existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Plano de aula atualizado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlanoAulaDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Plano de aula não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<PlanoAulaDTO> atualizarPlanoAula(
            @Parameter(
                    description = "Identificador do plano de aula.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados do plano de aula.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlanoAulaDTO.class)
                    )
            )
            @RequestBody @Valid PlanoAulaDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(planoAulaService.atualizarPlanoAula(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir plano de aula",
            description = "Exclui um plano de aula existente do sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Plano de aula excluído com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Plano de aula não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<ApiResponseDTO> deletarPlanoAula(
            @Parameter(
                    description = "Identificador do plano de aula.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(planoAulaService.deletarPlanoAula(id));
    }

    @GetMapping("/usuario/{id}")
    @Operation(
            summary = "Listar planos de aula por autor",
            description = "Retorna todos os planos de aula criados por um usuário (autor) específico."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Planos de aula retornados com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlanoAulaDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Autor não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<List<PlanoAulaDTO>> listarPlanosAulaAutor(
            @Parameter(
                    description = "Identificador do usuário (autor).",
                    example = "1",
                    required = true
            )
            @PathVariable("id") Long autorId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(planoAulaService.listarPlanosAulaAutor(autorId));
    }
}