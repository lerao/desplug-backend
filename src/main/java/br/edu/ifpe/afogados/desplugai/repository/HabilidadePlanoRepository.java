package br.edu.ifpe.afogados.desplugai.repository;

import br.edu.ifpe.afogados.desplugai.entity.HabilidadePlano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabilidadePlanoRepository extends JpaRepository<HabilidadePlano, Long> {
}
