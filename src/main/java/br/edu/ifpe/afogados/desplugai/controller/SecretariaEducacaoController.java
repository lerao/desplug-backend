package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.SecretariaEducacaoDTO;
import br.edu.ifpe.afogados.desplugai.service.SecretariaEducacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secretaria-educacao")
public class SecretariaEducacaoController {

    private final SecretariaEducacaoService secretariaEducacaoService;

    public SecretariaEducacaoController(SecretariaEducacaoService secretariaEducacaoService) {
        this.secretariaEducacaoService = secretariaEducacaoService;
    }

    @PostMapping
    public ResponseEntity<SecretariaEducacaoDTO> salvarSecretariaEducacao(
            @RequestBody @Valid SecretariaEducacaoDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(secretariaEducacaoService.salvarSecretariaEducacao(dto));
    }

    @GetMapping
    public ResponseEntity<List<SecretariaEducacaoDTO>> listarSecretariasEducacao() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(secretariaEducacaoService.listarSecretariasEducacao());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecretariaEducacaoDTO> buscarSecretariaEducacao(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(secretariaEducacaoService.buscarSecretariaEducacao(id));
    }
}