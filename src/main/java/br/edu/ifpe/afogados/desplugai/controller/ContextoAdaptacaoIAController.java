package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.ContextoAdaptacaoIADTO;
import br.edu.ifpe.afogados.desplugai.service.ContextoAdaptacaoIAService;
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
@RequestMapping("/contexto-adaptacao-ia")
@Tag(
        name = "Contextos de Adaptação IA",
        description = "Operações relacionadas ao gerenciamento de contextos de adaptação de IA."
)
public class ContextoAdaptacaoIAController {

    private final ContextoAdaptacaoIAService contextoAdaptacaoIAService;

    public ContextoAdaptacaoIAController(
            ContextoAdaptacaoIAService contextoAdaptacaoIAService
    ) {
        this.contextoAdaptacaoIAService = contextoAdaptacaoIAService;
    }

    @PostMapping
    @Operation(
            summary = "Criar contexto de adaptação IA",
            description = "Cria um novo contexto de adaptação de IA no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Contexto de adaptação IA criado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ContextoAdaptacaoIADTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            )
    })
    public ResponseEntity<ContextoAdaptacaoIADTO> salvarContextoAdaptacaoIA(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do contexto de adaptação IA que será criado.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ContextoAdaptacaoIADTO.class)
                    )
            )
            @RequestBody @Valid ContextoAdaptacaoIADTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contextoAdaptacaoIAService.salvarContextoAdaptacaoIA(dto));
    }

    @GetMapping
    @Operation(
            summary = "Listar contextos de adaptação IA",
            description = "Retorna todos os contextos de adaptação de IA cadastrados no sistema."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Contextos de adaptação IA retornados com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = ContextoAdaptacaoIADTO.class
                    )
            )
    )
    public ResponseEntity<List<ContextoAdaptacaoIADTO>> listarContextosAdaptacaoIA() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contextoAdaptacaoIAService.listarContextosAdaptacaoIA());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar contexto de adaptação IA por ID",
            description = "Retorna um contexto de adaptação de IA específico a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contexto de adaptação IA encontrado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ContextoAdaptacaoIADTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contexto de adaptação IA não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<ContextoAdaptacaoIADTO> buscarContextoAdaptacaoIA(
            @Parameter(
                    description = "Identificador do contexto de adaptação IA",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contextoAdaptacaoIAService.buscarContextoAdaptacaoIA(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar contexto de adaptação IA",
            description = "Atualiza os dados de um contexto de adaptação de IA existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contexto de adaptação IA atualizado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ContextoAdaptacaoIADTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contexto de adaptação IA não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<ContextoAdaptacaoIADTO> atualizarContextoAdaptacaoIA(
            @Parameter(
                    description = "Identificador do contexto de adaptação IA.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados do contexto de adaptação IA.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ContextoAdaptacaoIADTO.class)
                    )
            )
            @RequestBody @Valid ContextoAdaptacaoIADTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contextoAdaptacaoIAService.atualizarContextoAdaptacaoIA(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir contexto de adaptação IA",
            description = "Exclui um contexto de adaptação de IA existente do sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contexto de adaptação IA excluído com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contexto de adaptação IA não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<ApiResponseDTO> deletarContextoAdaptacaoIA(
            @Parameter(
                    description = "Identificador do contexto de adaptação IA.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contextoAdaptacaoIAService.deletarContextoAdaptacaoIA(id));
    }
}