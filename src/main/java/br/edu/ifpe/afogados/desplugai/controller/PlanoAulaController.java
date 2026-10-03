package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.*;
import br.edu.ifpe.afogados.desplugai.service.ContextoAdaptacaoIAService;
import br.edu.ifpe.afogados.desplugai.service.PlanoAulaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plano-aula")
public class PlanoAulaController {

    private final PlanoAulaService planoAulaService;
    private final ContextoAdaptacaoIAService contextoAdaptacaoIAService;

    public PlanoAulaController(PlanoAulaService planoAulaService, ContextoAdaptacaoIAService contextoAdaptacaoIAService) {
        this.planoAulaService = planoAulaService;
        this.contextoAdaptacaoIAService = contextoAdaptacaoIAService;
    }

    @PostMapping
    public ResponseEntity<PlanoAulaDTO> salvarPlanoAula(
            @RequestBody @Valid PlanoAulaDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(planoAulaService.salvarPlanoAula(dto));
    }

    @GetMapping
    public ResponseEntity<List<PlanoAulaDTO>> listarPlanosAula() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(planoAulaService.listarPlanosAula());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoAulaDTO> buscarPlanoAula(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(planoAulaService.buscarPlanoAula(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoAulaDTO> atualizarPlanoAula(
            @PathVariable Long id,
            @RequestBody @Valid PlanoAulaDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(planoAulaService.atualizarPlanoAula(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> deletarPlanoAula(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(planoAulaService.deletarPlanoAula(id));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<PlanoAulaDTO>> listarPlanosAulaAutor(@PathVariable Long autorId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(planoAulaService.listarPlanosAulaAutor(autorId));
    }

    @PostMapping("/{idPlanoBase}/adaptar")
    public ResponseEntity<PlanoAulaDTO> gerarAdaptacaoIA(
            @PathVariable Long idPlanoBase,
            @RequestBody @Valid AdaptacaoPlanoInput adaptacaoPlanoInput
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contextoAdaptacaoIAService.gerarAdaptacaoIA(idPlanoBase, adaptacaoPlanoInput));
    }
}
