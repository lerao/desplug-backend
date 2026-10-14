package br.edu.ifpe.afogados.desplugai.repository;

import br.edu.ifpe.afogados.desplugai.entity.ProfessorAfiliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorAfiliacaoRepository extends JpaRepository<ProfessorAfiliacao, Long> {

    Optional<ProfessorAfiliacao> findByProfessor_IdAndSecretaria_Id(Long usuarioId, Long secretariaId);
}