package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ContextoAdaptacaoIADTO;
import br.edu.ifpe.afogados.desplugai.dto.LoginRequestDTO;
import br.edu.ifpe.afogados.desplugai.dto.SessaoUsuarioDTO;
import br.edu.ifpe.afogados.desplugai.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")

@Tag(
        name = "Funcionalidades de Autenticação",
        description = "Operações relacionadas a autenticação de usuários."
)
public class AuthController {

    private final AuthService authService;

    public AuthController(
            AuthService authService
    ) {
        this.authService = authService;
    }

    @PostMapping
    @Operation(
            summary = "Realizar login do usuário",
            description = "Autentica um usuário no sistema para acesso das funcionalidades."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login realizado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SessaoUsuarioDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou falha na validação.",
                    content = @Content
            )
    })
    public ResponseEntity<SessaoUsuarioDTO> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados de login do usuário.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginRequestDTO.class)
                    )
            )
            @RequestBody @Valid LoginRequestDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.login(dto));
    }
}
