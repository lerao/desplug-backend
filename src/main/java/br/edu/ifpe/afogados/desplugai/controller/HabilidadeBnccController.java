package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.HabilidadeBnccDTO;
import br.edu.ifpe.afogados.desplugai.service.HabilidadeBnccService;
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
@RequestMapping("/habilidade-bncc")
@Tag(
        name = "Habilidades BNCC",
        description = "Operações relacionadas ao gerenciamento de habilidades da BNCC."
)
public class HabilidadeBnccController {

    private final HabilidadeBnccService habilidadeBnccService;

    public HabilidadeBnccController(HabilidadeBnccService habilidadeBnccService) {
        this.habilidadeBnccService = habilidadeBnccService;
    }

    @PostMapping
    @Operation(
            summary = "Criar habilidade BNCC",
            description = "Cria uma nova habilidade da BNCC no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Habilidade da BNCC criada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadeBnccDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            )
    })
    public ResponseEntity<HabilidadeBnccDTO> salvarHabilidadeBncc(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da habilidade da BNCC que será criada.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadeBnccDTO.class)
                    )
            )
            @RequestBody @Valid HabilidadeBnccDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(habilidadeBnccService.salvarHabilidadeBncc(dto));
    }

    @GetMapping
    @Operation(
            summary = "Listar habilidades BNCC",
            description = "Retorna todas as habilidades da BNCC cadastradas no sistema."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Habilidades da BNCC retornadas com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = HabilidadeBnccDTO.class
                    )
            )
    )
    public ResponseEntity<List<HabilidadeBnccDTO>> listarHabilidadesBncc() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(habilidadeBnccService.listarHabilidadesBncc());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar habilidade BNCC por ID",
            description = "Retorna uma habilidade da BNCC específica a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Habilidade da BNCC encontrada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadeBnccDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Habilidade da BNCC não encontrada.",
                    content = @Content
            )
    })
    public ResponseEntity<HabilidadeBnccDTO> buscarHabilidadeBncc(
            @Parameter(
                    description = "Identificador da habilidade BNCC",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(habilidadeBnccService.buscarHabilidadeBncc(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar habilidade BNCC",
            description = "Atualiza os dados de uma habilidade da BNCC existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Habilidade da BNCC atualizada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadeBnccDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Habilidade da BNCC não encontrada.",
                    content = @Content
            )
    })
    public ResponseEntity<HabilidadeBnccDTO> atualizarHabilidadeBncc(
            @Parameter(
                    description = "Identificador da habilidade BNCC.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados da habilidade BNCC.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadeBnccDTO.class)
                    )
            )
            @RequestBody @Valid HabilidadeBnccDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(habilidadeBnccService.atualizarHabilidadeBncc(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir habilidade BNCC",
            description = "Exclui uma habilidade da BNCC existente do sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Habilidade da BNCC excluída com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Habilidade da BNCC não encontrada.",
                    content = @Content
            )
    })
    public ResponseEntity<ApiResponseDTO> deletarHabilidadeBncc(
            @Parameter(
                    description = "Identificador da habilidade BNCC.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(habilidadeBnccService.deletarHabilidadeBncc(id));
    }
}