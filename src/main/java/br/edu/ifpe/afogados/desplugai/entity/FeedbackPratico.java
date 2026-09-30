package br.edu.ifpe.afogados.desplugai.entity;

import br.edu.ifpe.afogados.desplugai.enums.FeedbackPraticoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "Feedback_Pratico")
public class FeedbackPratico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_plano", nullable = false)
    private PlanoAula plano;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String relatoExperiencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedbackPraticoEnum avaliacaoGeral;

    @Column(nullable = false)
    private Integer fotosEvidencias;

    @Column(nullable = false)
    private Timestamp dataAplicacao;

    @Column(nullable = false)
    private Timestamp dataRegistro;
}