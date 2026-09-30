package br.edu.ifpe.afogados.desplugai.repository;

import br.edu.ifpe.afogados.desplugai.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
