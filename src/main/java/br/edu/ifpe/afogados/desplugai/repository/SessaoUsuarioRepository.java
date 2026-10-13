package br.edu.ifpe.afogados.desplugai.repository;

import br.edu.ifpe.afogados.desplugai.entity.SessaoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessaoUsuarioRepository extends JpaRepository<SessaoUsuario, Long> {

    Optional<SessaoUsuario> findByToken(String token);
}
