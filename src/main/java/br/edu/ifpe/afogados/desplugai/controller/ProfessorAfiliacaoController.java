package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ProfessorAfiliacaoDTO;
import br.edu.ifpe.afogados.desplugai.service.ProfessorAfiliacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professor-afiliacao")
public class ProfessorAfiliacaoController {

    private final ProfessorAfiliacaoService professorAfiliacaoService;

    public ProfessorAfiliacaoController(ProfessorAfiliacaoService professorAfiliacaoService) {
        this.professorAfiliacaoService = professorAfiliacaoService;
    }

    @PostMapping
    public ResponseEntity<ProfessorAfiliacaoDTO> salvarProfessorAfiliacao(
            @RequestBody @Valid ProfessorAfiliacaoDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(professorAfiliacaoService.salvarProfessorAfiliacao(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProfessorAfiliacaoDTO>> listarProfessorAfiliacoes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(professorAfiliacaoService.listarProfessorAfiliacoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorAfiliacaoDTO> buscarProfessorAfiliacao(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(professorAfiliacaoService.buscarProfessorAfiliacao(id));
    }
}
