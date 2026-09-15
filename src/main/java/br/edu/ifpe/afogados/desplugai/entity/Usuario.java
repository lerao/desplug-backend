package br.edu.ifpe.afogados.desplugai.entity;

import br.edu.ifpe.afogados.desplugai.enums.PerfilGlobalEnum;
import br.edu.ifpe.afogados.desplugai.enums.StatusHomologacaoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerfilGlobalEnum perfilGlobal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusHomologacaoEnum statusHomologacao;

    @Column(nullable = false)
    private Timestamp dataRegistro;

    @Column(nullable = true)
    private Timestamp dataHomologacao;

    @Column(nullable = true)
    private String usuarioHomologacao;

}
