package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.FavoritoDTO;
import br.edu.ifpe.afogados.desplugai.service.FavoritoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorito")
public class FavoritoController {

    private final FavoritoService favoritoService;

    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }

    @PostMapping
    public ResponseEntity<FavoritoDTO> salvarFavorito(
            @RequestBody @Valid FavoritoDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(favoritoService.salvarFavorito(dto));
    }

    @GetMapping
    public ResponseEntity<List<FavoritoDTO>> listarFavoritos() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(favoritoService.listarFavoritos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoritoDTO> buscarFavorito(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(favoritoService.buscarFavorito(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FavoritoDTO> atualizarFavorito(
            @PathVariable Long id,
            @RequestBody @Valid FavoritoDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(favoritoService.atualizarFavorito(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> deletarFavorito(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(favoritoService.deletarFavorito(id));
    }
}