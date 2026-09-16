package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.ContextoAdaptacaoIADTO;
import br.edu.ifpe.afogados.desplugai.service.ContextoAdaptacaoIAService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contexto-adaptacao-ia")
public class ContextoAdaptacaoIAController {

    private final ContextoAdaptacaoIAService contextoAdaptacaoIAService;

    public ContextoAdaptacaoIAController(
            ContextoAdaptacaoIAService contextoAdaptacaoIAService
    ) {
        this.contextoAdaptacaoIAService = contextoAdaptacaoIAService;
    }

    @PostMapping
    public ResponseEntity<ContextoAdaptacaoIADTO> salvarContextoAdaptacaoIA(
            @RequestBody @Valid ContextoAdaptacaoIADTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contextoAdaptacaoIAService.salvarContextoAdaptacaoIA(dto));
    }

    @GetMapping
    public ResponseEntity<List<ContextoAdaptacaoIADTO>> listarContextosAdaptacaoIA() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contextoAdaptacaoIAService.listarContextosAdaptacaoIA());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContextoAdaptacaoIADTO> buscarContextoAdaptacaoIA(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contextoAdaptacaoIAService.buscarContextoAdaptacaoIA(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContextoAdaptacaoIADTO> atualizarContextoAdaptacaoIA(
            @PathVariable Long id,
            @RequestBody @Valid ContextoAdaptacaoIADTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contextoAdaptacaoIAService.atualizarContextoAdaptacaoIA(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> deletarContextoAdaptacaoIA(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contextoAdaptacaoIAService.deletarContextoAdaptacaoIA(id));
    }
}
