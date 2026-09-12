package br.edu.ifpe.afogados.desplugai.entity;

import br.edu.ifpe.afogados.desplugai.enums.EtapaEnsinoEnum;
import br.edu.ifpe.afogados.desplugai.enums.StatusPublicacaoEnum;
import br.edu.ifpe.afogados.desplugai.enums.TipoAtividadeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class PlanoAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String resumo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAtividadeEnum tipoAtividade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtapaEnsinoEnum etapaEnsino;

    @Column(nullable = false)
    private String anosIndicados;

    @Column(nullable = false)
    private String duracao;

    @Column(nullable = false)
    private String componentesCurriculares;

    @Column(nullable = false)
    private String materiaisNecessarios;

    @Column(nullable = false)
    private String metodologia;

    @Column(nullable = false)
    private String criteriosAvaliacao;

    @Column(nullable = false)
    private String imagemCapa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPublicacaoEnum statusPublicacao;

    @Column(nullable = false)
    private String idAutor;

    @Column(nullable = false)
    public Boolean isGeradoIA;

    @Column(nullable = false)
    private Boolean isDerivado;

    @Column(nullable = true)
    private String idPlanoOrigem;

    @Column(nullable = false)
    private Integer visualizacoes;

    @Column(nullable = false)
    private Timestamp dataCriacao;

    @Column(nullable = true)
    private Timestamp dataAtualizacao;

}
