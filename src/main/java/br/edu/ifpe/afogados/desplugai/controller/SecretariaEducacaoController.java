package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.SecretariaEducacaoDTO;
import br.edu.ifpe.afogados.desplugai.service.SecretariaEducacaoService;
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
@RequestMapping("/secretaria-educacao")
@Tag(
        name = "Secretarias de Educação",
        description = "Operações relacionadas ao gerenciamento de secretarias de educação."
)
public class SecretariaEducacaoController {

    private final SecretariaEducacaoService secretariaEducacaoService;

    public SecretariaEducacaoController(
            SecretariaEducacaoService secretariaEducacaoService
    ) {
        this.secretariaEducacaoService = secretariaEducacaoService;
    }

    @PostMapping
    @Operation(
            summary = "Criar secretaria de educação",
            description = "Cria uma nova secretaria de educação no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Secretaria de educação criada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SecretariaEducacaoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            )
    })
    public ResponseEntity<SecretariaEducacaoDTO> salvarSecretariaEducacao(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da secretaria de educação que será criada.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SecretariaEducacaoDTO.class)
                    )
            )
            @RequestBody @Valid SecretariaEducacaoDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        secretariaEducacaoService
                                .salvarSecretariaEducacao(dto)
                );
    }

    @GetMapping
    @Operation(
            summary = "Listar secretarias de educação",
            description = "Retorna todas as secretarias de educação cadastradas no sistema."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Secretarias de educação retornadas com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = SecretariaEducacaoDTO.class
                    )
            )
    )
    public ResponseEntity<List<SecretariaEducacaoDTO>> listarSecretariasEducacao() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        secretariaEducacaoService
                                .listarSecretariasEducacao()
                );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar secretaria de educação por ID",
            description = "Retorna uma secretaria de educação específica a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Secretaria de educação encontrada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SecretariaEducacaoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Secretaria de educação não encontrada.",
                    content = @Content
            )
    })
    public ResponseEntity<SecretariaEducacaoDTO> buscarSecretariaEducacao(
            @Parameter(
                    description = "Identificador da secretaria de educação",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        secretariaEducacaoService
                                .buscarSecretariaEducacao(id)
                );
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar secretaria de educação",
            description = "Atualiza os dados de uma secretaria de educação existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Secretaria de educação atualizada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SecretariaEducacaoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Secretaria de educação não encontrada.",
                    content = @Content
            )
    })
    public ResponseEntity<SecretariaEducacaoDTO> atualizarSecretariaEducacao(
            @Parameter(
                    description = "Identificador da secretaria de educação.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados da secretaria de educação.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SecretariaEducacaoDTO.class)
                    )
            )
            @RequestBody @Valid SecretariaEducacaoDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        secretariaEducacaoService
                                .atualizarSecretariaEducacao(id, dto)
                );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir secretaria de educação",
            description = "Exclui uma secretaria de educação existente do sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Secretaria de educação excluída com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Secretaria de educação não encontrada.",
                    content = @Content
            )
    })
    public ResponseEntity<ApiResponseDTO> deletarSecretariaEducacao(
            @Parameter(
                    description = "Identificador da secretaria de educação.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        secretariaEducacaoService
                                .deletarSecretariaEducacao(id)
                );
    }
}