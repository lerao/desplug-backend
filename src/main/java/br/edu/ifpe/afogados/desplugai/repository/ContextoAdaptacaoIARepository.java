package br.edu.ifpe.afogados.desplugai.repository;

import br.edu.ifpe.afogados.desplugai.entity.ContextoAdaptacaoIA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContextoAdaptacaoIARepository extends JpaRepository<ContextoAdaptacaoIA, Long> {
}
