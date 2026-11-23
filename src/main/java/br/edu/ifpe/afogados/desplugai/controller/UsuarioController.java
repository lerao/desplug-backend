package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.ProfessorAfiliacaoDTO;
import br.edu.ifpe.afogados.desplugai.dto.UsuarioDTO;
import br.edu.ifpe.afogados.desplugai.enums.StatusHomologacaoEnum;
import br.edu.ifpe.afogados.desplugai.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@Tag(
        name = "Usuários",
        description = "Operações relacionadas ao gerenciamento de usuários."
)
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Operation(
            summary = "Criar usuário",
            description = "Cria um novo usuário no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            )
    })
    public ResponseEntity<UsuarioDTO> salvarUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do usuário que será criado.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class)
                    )
            )
            @RequestBody @Valid UsuarioDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.salvarUsuario(dto));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SECRETARIA')")
    @GetMapping
    @Operation(
            summary = "Listar usuários",
            description = "Retorna todos os usuários cadastrados no sistema."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Usuários retornados com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = Page.class
                    )
            )
    )
    public ResponseEntity<Page<UsuarioDTO>> listarUsuarios(
            @ParameterObject
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.listarUsuarios(pageable));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna um usuário específico a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<UsuarioDTO> buscarUsuario(
            @Parameter(
                    description = "Identificador do usuário",
                    example = "2",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.buscarUsuario(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<UsuarioDTO> atualizarUsuario(
            @Parameter(
                    description = "Identificador do usuário.",
                    example = "2",
                    required = true
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados do usuário.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class)
                    )
            )
            @RequestBody @Valid UsuarioDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.atualizarUsuario(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir usuário",
            description = "Exclui um usuário existente do sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário excluído com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<ApiResponseDTO> deletarUsuario(
            @Parameter(
                    description = "Identificador do usuário.",
                    example = "2",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.deletarUsuario(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SECRETARIA')")
    @GetMapping("/{id}/{status}")
    @Operation(
            summary = "Homologar usuário",
            description = "Homologa um usuário existente do sistema, alterandos seu status de PENDENTE para HOMOLOGADO."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário homologado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<ApiResponseDTO> alterarStatusUsuario(
            @Parameter(
                    description = "Identificador do usuário.",
                    example = "2",
                    required = true
            )
            @PathVariable Long id,
            @Parameter(
                    description = "Status do usuário para ser alterado.",
                    examples = {@ExampleObject(value = "APROVADO"), @ExampleObject(value = "REPROVADO")}
            )
            @PathVariable StatusHomologacaoEnum status
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.alterarStatusUsuario(id, status));
    }


    @GetMapping("/perfil")
    @Operation(
            summary = "Obter perfil do usuário",
            description = "Retorna o perfil de um usuário existente do sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfil do usuário retornado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProfessorAfiliacaoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado.",
                    content = @Content
            )
    })
    public ResponseEntity<ProfessorAfiliacaoDTO> obterPerfilUsuario(
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long idUsuario = Long.valueOf(jwt.getSubject());
        return ResponseEntity.ok(usuarioService.obterPerfilUsuario(idUsuario));
    }
}
