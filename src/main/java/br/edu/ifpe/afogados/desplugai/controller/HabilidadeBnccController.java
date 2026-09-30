package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.HabilidadeBnccDTO;
import br.edu.ifpe.afogados.desplugai.service.HabilidadeBnccService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habilidade-bncc")
public class HabilidadeBnccController {

    private final HabilidadeBnccService habilidadeBnccService;

    public HabilidadeBnccController(HabilidadeBnccService habilidadeBnccService) {
        this.habilidadeBnccService = habilidadeBnccService;
    }

    @PostMapping
    public ResponseEntity<HabilidadeBnccDTO> salvarHabilidadeBncc(
            @RequestBody @Valid HabilidadeBnccDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(habilidadeBnccService.salvarHabilidadeBncc(dto));
    }

    @GetMapping
    public ResponseEntity<List<HabilidadeBnccDTO>> listarHabilidadesBncc() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(habilidadeBnccService.listarHabilidadesBncc());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabilidadeBnccDTO> buscarHabilidadeBncc(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(habilidadeBnccService.buscarHabilidadeBncc(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabilidadeBnccDTO> atualizarHabilidadeBncc(
            @PathVariable Long id,
            @RequestBody @Valid HabilidadeBnccDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(habilidadeBnccService.atualizarHabilidadeBncc(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> deletarHabilidadeBncc(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(habilidadeBnccService.deletarHabilidadeBncc(id));
    }
}