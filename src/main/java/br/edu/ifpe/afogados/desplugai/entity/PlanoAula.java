package br.edu.ifpe.afogados.desplugai.entity;

import br.edu.ifpe.afogados.desplugai.enums.EtapaEnsinoEnum;
import br.edu.ifpe.afogados.desplugai.enums.StatusPublicacaoEnum;
import br.edu.ifpe.afogados.desplugai.enums.TipoAtividadeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
public class PlanoAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
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

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String metodologia;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String criteriosAvaliacao;

    @OneToMany(mappedBy = "plano", orphanRemoval = true)
    private List<HabilidadePlano> habilidadesPlano;

    @Column(nullable = false)
    private String imagemCapa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPublicacaoEnum statusPublicacao;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Usuario autor;

    @Column(nullable = false)
    public Boolean isGeradoIA;

    @Column(nullable = false)
    private Boolean isDerivado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plano_origem", nullable = true)
    private PlanoAula planoOrigem;

    @Column(nullable = false)
    private Integer visualizacoes;

    @Column(nullable = false)
    private Timestamp dataCriacao;

    @Column(nullable = true)
    private Timestamp dataAtualizacao;

}
