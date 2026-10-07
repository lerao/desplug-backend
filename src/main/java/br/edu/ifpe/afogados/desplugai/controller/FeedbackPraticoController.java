package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.FeedbackPraticoDTO;
import br.edu.ifpe.afogados.desplugai.service.FeedbackPraticoService;
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
@RequestMapping("/feedback-pratico")
@Tag(
        name = "Feedbacks Práticos",
        description = "Operações relacionadas ao gerenciamento de feedbacks práticos."
)
public class FeedbackPraticoController {

    private final FeedbackPraticoService feedbackPraticoService;

    public FeedbackPraticoController(
            FeedbackPraticoService feedbackPraticoService
    ) {
        this.feedbackPraticoService = feedbackPraticoService;
    }

    @PostMapping
    @Operation(
            summary = "Criar feedback prático",
            description = "Cria um novo feedback prático no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Feedback prático criado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FeedbackPraticoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            )
    })
    public ResponseEntity<FeedbackPraticoDTO> salvarFeedbackPratico(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do feedback prático que será criado.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FeedbackPraticoDTO.class)
                    )
            )
            @RequestBody @Valid FeedbackPraticoDTO dto
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        feedbackPraticoService
                                .salvarFeedbackPratico(dto)
                );
    }

    @GetMapping
    @Operation(
            summary = "Listar feedbacks práticos",
            description = "Retorna todos os feedbacks práticos cadastrados no sistema."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Feedbacks práticos retornados com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = FeedbackPraticoDTO.class
                    )
            )
    )
    public ResponseEntity<List<FeedbackPraticoDTO>>
    listarFeedbacksPraticos() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        feedbackPraticoService
                                .listarFeedbacksPraticos()
                );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar feedback prático por ID",
            description = "Retorna um feedback prático específico a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Feedback prático encontrado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FeedbackPraticoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Feedback prático não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<FeedbackPraticoDTO>
    buscarFeedbackPratico(
            @Parameter(
                    description = "Identificador do feedback prático",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        feedbackPraticoService
                                .buscarFeedbackPratico(id)
                );
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar feedback prático",
            description = "Atualiza os dados de um feedback prático existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Feedback prático atualizado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FeedbackPraticoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Feedback prático não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<FeedbackPraticoDTO>
    atualizarFeedbackPratico(
            @Parameter(
                    description = "Identificador do feedback prático.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados do feedback prático.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FeedbackPraticoDTO.class)
                    )
            )
            @RequestBody @Valid FeedbackPraticoDTO dto
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        feedbackPraticoService
                                .atualizarFeedbackPratico(id, dto)
                );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir feedback prático",
            description = "Exclui um feedback prático existente do sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Feedback prático excluído com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Feedback prático não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<ApiResponseDTO>
    deletarFeedbackPratico(
            @Parameter(
                    description = "Identificador do feedback prático.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        feedbackPraticoService
                                .deletarFeedbackPratico(id)
                );
    }
}