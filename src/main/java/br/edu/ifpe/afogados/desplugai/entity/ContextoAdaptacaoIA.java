package br.edu.ifpe.afogados.desplugai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ContextoAdaptacaoIA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idProfessor;

    private Long idPlanoBase;

    private Long idPlanoGerado;

    private String materiaisDisponiveis;

    private String perfilTurma;

    private String habilidadeFoco;

    private String observacoesInstrucoes;

    @Lob
    private String respostaIA;

    private Integer tokensConsumidos;

    private LocalDateTime dataInteracao;
}
