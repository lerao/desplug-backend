package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.HabilidadePlanoDTO;
import br.edu.ifpe.afogados.desplugai.service.HabilidadePlanoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habilidade-plano")
public class HabilidadePlanoController {

    private final HabilidadePlanoService habilidadePlanoService;

    public HabilidadePlanoController(HabilidadePlanoService habilidadePlanoService) {
        this.habilidadePlanoService = habilidadePlanoService;
    }

    @PostMapping
    public ResponseEntity<HabilidadePlanoDTO> salvarHabilidadePlano(
            @RequestBody @Valid HabilidadePlanoDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(habilidadePlanoService.salvarHabilidadePlano(dto));
    }

    @GetMapping
    public ResponseEntity<List<HabilidadePlanoDTO>> listarHabilidadesPlano() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(habilidadePlanoService.listarHabilidadesPlano());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabilidadePlanoDTO> buscarHabilidadePlano(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(habilidadePlanoService.buscarHabilidadePlano(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabilidadePlanoDTO> atualizarHabilidadePlano(
            @PathVariable Long id,
            @RequestBody @Valid HabilidadePlanoDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(habilidadePlanoService.atualizarHabilidadePlano(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> deletarHabilidadePlano(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(habilidadePlanoService.deletarHabilidadePlano(id));
    }
}
