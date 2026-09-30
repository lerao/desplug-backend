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

    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private Usuario professor;

    @ManyToOne
    @JoinColumn(name = "id_plano_base")
    private PlanoAula planoBase;

    @ManyToOne
    @JoinColumn(name = "id_plano_gerado")
    private PlanoAula planoGerado;

    private String materiaisDisponiveis;

    private String perfilTurma;

    private String habilidadeFoco;

    private String observacoesInstrucoes;

    @Lob
    private String respostaIA;

    private Integer tokensConsumidos;

    private LocalDateTime dataInteracao;
}
