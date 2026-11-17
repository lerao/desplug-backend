package br.edu.ifpe.afogados.desplugai.repository;

import br.edu.ifpe.afogados.desplugai.entity.PerfilUsuario;
import br.edu.ifpe.afogados.desplugai.enums.PerfilGlobalEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilUsuarioRepository  extends JpaRepository<PerfilUsuario, Long> {

    boolean existsByUsuario_IdAndPerfil(Long usuarioId, PerfilGlobalEnum perfil);
}
