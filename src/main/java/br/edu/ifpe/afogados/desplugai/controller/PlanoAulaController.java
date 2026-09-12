package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.PlanoAulaDTO;
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

    public PlanoAulaController(PlanoAulaService planoAulaService) {
        this.planoAulaService = planoAulaService;
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
}
