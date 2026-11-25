package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.FavoritoDTO;
import br.edu.ifpe.afogados.desplugai.service.FavoritoService;
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
@RequestMapping("/favorito")
@Tag(
        name = "Favoritos",
        description = "Operações relacionadas ao gerenciamento de favoritos."
)
public class FavoritoController {

    private final FavoritoService favoritoService;

    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }

    @PostMapping
    @Operation(
            summary = "Criar favorito",
            description = "Cria um novo favorito no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Favorito criado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FavoritoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            )
    })
    public ResponseEntity<FavoritoDTO> salvarFavorito(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do favorito que será criado.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FavoritoDTO.class)
                    )
            )
            @RequestBody @Valid FavoritoDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(favoritoService.salvarFavorito(dto));
    }

    @GetMapping
    @Operation(
            summary = "Listar favoritos",
            description = "Retorna todos os favoritos cadastrados no sistema."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Favoritos retornados com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = FavoritoDTO.class
                    )
            )
    )
    public ResponseEntity<List<FavoritoDTO>> listarFavoritos() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(favoritoService.listarFavoritos());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar favorito por ID",
            description = "Retorna um favorito específico a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Favorito encontrado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FavoritoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Favorito não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<FavoritoDTO> buscarFavorito(
            @Parameter(
                    description = "Identificador do favorito",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(favoritoService.buscarFavorito(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar favorito",
            description = "Atualiza os dados de um favorito existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Favorito atualizado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FavoritoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Favorito não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<FavoritoDTO> atualizarFavorito(
            @Parameter(
                    description = "Identificador do favorito.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados do favorito.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FavoritoDTO.class)
                    )
            )
            @RequestBody @Valid FavoritoDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(favoritoService.atualizarFavorito(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir favorito",
            description = "Exclui um favorito existente do sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Favorito excluído com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Favorito não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<ApiResponseDTO> deletarFavorito(
            @Parameter(
                    description = "Identificador do favorito.",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(favoritoService.deletarFavorito(id));
    }
}