package br.edu.ifpe.afogados.desplugai.repository;

import br.edu.ifpe.afogados.desplugai.entity.HabilidadeBncc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabilidadeBnccRepository extends JpaRepository<HabilidadeBncc, Long> {

    boolean existsByCodigo(String codigo);

    Optional<HabilidadeBncc> findByCodigo(String codigo);
}