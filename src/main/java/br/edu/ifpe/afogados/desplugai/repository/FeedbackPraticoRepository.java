package br.edu.ifpe.afogados.desplugai.repository;

import br.edu.ifpe.afogados.desplugai.entity.FeedbackPratico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackPraticoRepository
        extends JpaRepository<FeedbackPratico, Long> {
}