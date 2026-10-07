package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ProfessorAfiliacaoDTO;
import br.edu.ifpe.afogados.desplugai.service.ProfessorAfiliacaoService;
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
import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import java.util.List;

@RestController
@RequestMapping("/professor-afiliacao")
@Tag(
        name = "Afiliações de Professores",
        description = "Operações relacionadas ao gerenciamento de afiliações de professores."
)
public class ProfessorAfiliacaoController {

    private final ProfessorAfiliacaoService professorAfiliacaoService;

    public ProfessorAfiliacaoController(ProfessorAfiliacaoService professorAfiliacaoService) {
        this.professorAfiliacaoService = professorAfiliacaoService;
    }

    @PostMapping
    @Operation(
            summary = "Criar afiliação de professor",
            description = "Cria uma nova afiliação de professor no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Afiliação de professor criada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProfessorAfiliacaoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            )
    })
    public ResponseEntity<ProfessorAfiliacaoDTO> salvarProfessorAfiliacao(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da afiliação de professor que será criada.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProfessorAfiliacaoDTO.class)
                    )
            )
            @RequestBody @Valid ProfessorAfiliacaoDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(professorAfiliacaoService.salvarProfessorAfiliacao(dto));
    }

    @GetMapping
    @Operation(
            summary = "Listar afiliações de professores",
            description = "Retorna todas as afiliações de professores cadastradas no sistema."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Afiliações de professores retornadas com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = ProfessorAfiliacaoDTO.class
                    )
            )
    )
    public ResponseEntity<List<ProfessorAfiliacaoDTO>> listarProfessorAfiliacoes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(professorAfiliacaoService.listarProfessorAfiliacoes());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar afiliação de professor por ID",
            description = "Retorna uma afiliação de professor específica a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Afiliação de professor encontrada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProfessorAfiliacaoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Afiliação de professor não encontrada.",
                    content = @Content
            )
    })
    public ResponseEntity<ProfessorAfiliacaoDTO> buscarProfessorAfiliacao(
            @Parameter(
                    description = "Identificador da afiliação de professor",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(professorAfiliacaoService.buscarProfessorAfiliacao(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar afiliação de professor",
            description = "Atualiza os dados de uma afiliação de professor existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Afiliação de professor atualizada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProfessorAfiliacaoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Afiliação de professor não encontrada.",
                    content = @Content
            )
    })
    public ResponseEntity<ProfessorAfiliacaoDTO> atualizarProfessorAfiliacao(
            @Parameter(
                    description = "Identificador da afiliação de professor.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados da afiliação de professor.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProfessorAfiliacaoDTO.class)
                    )
            )
            @RequestBody @Valid ProfessorAfiliacaoDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(professorAfiliacaoService.atualizarProfessorAfiliacao(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir afiliação de professor",
            description = "Exclui uma afiliação de professor existente do sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Afiliação de professor excluída com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Afiliação de professor não encontrada.",
                    content = @Content
            )
    })
    public ResponseEntity<ApiResponseDTO> deletarProfessorAfiliacao(
            @Parameter(
                    description = "Identificador da afiliação de professor.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(professorAfiliacaoService.deletarProfessorAfiliacao(id));
    }

}