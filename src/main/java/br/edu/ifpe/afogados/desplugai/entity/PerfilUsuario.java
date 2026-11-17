package br.edu.ifpe.afogados.desplugai.entity;

import br.edu.ifpe.afogados.desplugai.enums.PerfilGlobalEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "perfil_usuario",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_perfil_usuario_usuario_perfil",
                        columnNames = {"id_usuario", "perfil_global"}
                )
        }
)
public class PerfilUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerfilGlobalEnum perfil;
}
