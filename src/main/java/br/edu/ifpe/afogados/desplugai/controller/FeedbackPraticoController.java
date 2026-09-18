package br.edu.ifpe.afogados.desplugai.controller;

import br.edu.ifpe.afogados.desplugai.dto.ApiResponseDTO;
import br.edu.ifpe.afogados.desplugai.dto.FeedbackPraticoDTO;
import br.edu.ifpe.afogados.desplugai.service.FeedbackPraticoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback-pratico")
public class FeedbackPraticoController {

    private final FeedbackPraticoService feedbackPraticoService;

    public FeedbackPraticoController(
            FeedbackPraticoService feedbackPraticoService
    ) {
        this.feedbackPraticoService = feedbackPraticoService;
    }

    @PostMapping
    public ResponseEntity<FeedbackPraticoDTO> salvarFeedbackPratico(
            @RequestBody @Valid FeedbackPraticoDTO dto
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        feedbackPraticoService
                                .salvarFeedbackPratico(dto)
                );
    }

    @GetMapping
    public ResponseEntity<List<FeedbackPraticoDTO>>
    listarFeedbacksPraticos() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        feedbackPraticoService
                                .listarFeedbacksPraticos()
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackPraticoDTO>
    buscarFeedbackPratico(@PathVariable Long id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        feedbackPraticoService
                                .buscarFeedbackPratico(id)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackPraticoDTO>
    atualizarFeedbackPratico(
            @PathVariable Long id,
            @RequestBody @Valid FeedbackPraticoDTO dto
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        feedbackPraticoService
                                .atualizarFeedbackPratico(id, dto)
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO>
    deletarFeedbackPratico(@PathVariable Long id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        feedbackPraticoService
                                .deletarFeedbackPratico(id)
                );
    }
}