package br.edu.ifpe.afogados.desplugai.repository;

import br.edu.ifpe.afogados.desplugai.entity.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    boolean existsByUsuario_IdAndPlanoAula_Id(Long idUsuario, Long idPlano);

    @Query("""
        SELECT f.planoAula.id, COUNT(f.id)
        FROM Favorito f
        WHERE f.planoAula.id IN :planoIds
        GROUP BY f.planoAula.id
        """)
    List<Object[]> contarPorPlanoIds(@Param("planoIds") List<Long> planoIds);

    @Query("""
       SELECT COUNT(f.id)
       FROM Favorito f
       WHERE f.planoAula.id = :planoId
        """)
    Long contarPorPlanoId(@Param("planoId") Long planoId);

}