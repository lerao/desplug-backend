package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.HabilidadePlanoDTO;
import br.edu.ifpe.afogados.desplugai.service.HabilidadePlanoService;
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
@RequestMapping("/habilidade-plano")
@Tag(
        name = "Habilidades do Plano",
        description = "Operações relacionadas ao gerenciamento das habilidades vinculadas aos planos de aula."
)
public class HabilidadePlanoController {

    private final HabilidadePlanoService habilidadePlanoService;

    public HabilidadePlanoController(HabilidadePlanoService habilidadePlanoService) {
        this.habilidadePlanoService = habilidadePlanoService;
    }

    @PostMapping
    @Operation(
            summary = "Criar habilidade de plano",
            description = "Cria uma nova relação de habilidade para um plano de aula no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Habilidade do plano criada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadePlanoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            )
    })
    public ResponseEntity<HabilidadePlanoDTO> salvarHabilidadePlano(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da habilidade do plano que será criada.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadePlanoDTO.class)
                    )
            )
            @RequestBody @Valid HabilidadePlanoDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(habilidadePlanoService.salvarHabilidadePlano(dto));
    }

    @GetMapping
    @Operation(
            summary = "Listar habilidades do plano",
            description = "Retorna todas as habilidades vinculadas aos planos de aula no sistema."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Habilidades retornadas com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = HabilidadePlanoDTO.class
                    )
            )
    )
    public ResponseEntity<List<HabilidadePlanoDTO>> listarHabilidadesPlano() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(habilidadePlanoService.listarHabilidadesPlano());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar habilidade do plano por ID",
            description = "Retorna uma habilidade de plano específica a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Habilidade do plano encontrada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadePlanoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Habilidade do plano não encontrada.",
                    content = @Content
            )
    })
    public ResponseEntity<HabilidadePlanoDTO> buscarHabilidadePlano(
            @Parameter(
                    description = "Identificador da habilidade do plano",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(habilidadePlanoService.buscarHabilidadePlano(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar habilidade do plano",
            description = "Atualiza os dados de uma habilidade de plano existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Habilidade do plano atualizada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadePlanoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Habilidade do plano não encontrada.",
                    content = @Content
            )
    })
    public ResponseEntity<HabilidadePlanoDTO> atualizarHabilidadePlano(
            @Parameter(
                    description = "Identificador da habilidade do plano.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados da habilidade do plano.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HabilidadePlanoDTO.class)
                    )
            )
            @RequestBody @Valid HabilidadePlanoDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(habilidadePlanoService.atualizarHabilidadePlano(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir habilidade do plano",
            description = "Exclui uma habilidade de plano existente do sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Habilidade do plano excluída com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Habilidade do plano não encontrada.",
                    content = @Content
            )
    })
    public ResponseEntity<ApiResponseDTO> deletarHabilidadePlano(
            @Parameter(
                    description = "Identificador da habilidade do plano.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(habilidadePlanoService.deletarHabilidadePlano(id));
    }
}