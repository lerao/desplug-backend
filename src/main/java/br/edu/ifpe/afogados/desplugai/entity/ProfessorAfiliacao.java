package br.edu.ifpe.afogados.desplugai.entity;

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

    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private Usuario professor;

    @ManyToOne
    @JoinColumn(name = "id_secretaria", nullable = false)
    private SecretariaEducacao secretaria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAfiliacaoEnum status;

    @Column(nullable = true)
    private Timestamp dataVinculacao;
}



