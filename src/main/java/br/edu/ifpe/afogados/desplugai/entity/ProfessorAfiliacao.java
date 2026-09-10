package br.edu.ifpe.afogados.desplugai.entity;


import br.edu.ifpe.afogados.desplugai.enums.PerfilGlobalEnum;
import br.edu.ifpe.afogados.desplugai.enums.StatusAfiliacaoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class ProfessorAfiliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idProfessor;

    @Column(nullable = false)
    private Long idSecretaria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAfiliacaoEnum status;

    @Column(nullable = true)
    private Timestamp dataVinculacao;


}
