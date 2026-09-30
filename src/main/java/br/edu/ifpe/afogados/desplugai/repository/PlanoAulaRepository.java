package br.edu.ifpe.afogados.desplugai.repository;

import br.edu.ifpe.afogados.desplugai.entity.PlanoAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanoAulaRepository extends JpaRepository<PlanoAula, Long> {

    List<PlanoAula> findByAutor(Long autorId);
}
