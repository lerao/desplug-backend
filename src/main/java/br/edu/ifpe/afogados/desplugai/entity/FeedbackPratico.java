package br.edu.ifpe.afogados.desplugai.entity;

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

    @Column(nullable = false)
    private Long idPlano;

    @Column(nullable = false)
    private Long idProfessor;

    @Column(nullable = false)
    private String relatoExperiencia;

    @Column(nullable = false)
    private Integer avaliacaoGeral;

    @Column(nullable = false)
    private Integer fotosEvidencias;

    @Column(nullable = false)
    private Timestamp dataAplicacao;

    @Column(nullable = false)
    private Timestamp dataRegistro;
}