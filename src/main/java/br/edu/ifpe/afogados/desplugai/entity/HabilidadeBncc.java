package br.edu.ifpe.afogados.desplugai.entity;

import br.edu.ifpe.afogados.desplugai.enums.EixoComputacaoEnum;
import br.edu.ifpe.afogados.desplugai.enums.EtapaEnsinoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
    name = "habilidade_bncc",
    uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_habilidade_bncc_codigo",
                    columnNames = "codigo"
            )
    }
)
public class HabilidadeBncc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private EixoComputacaoEnum eixoComputacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtapaEnsinoEnum etapaEnsino;

    @Column(nullable = false)
    private String faixaEtaria;

    @Column(nullable = false)
    private String componenteCurricular;

    @Column(nullable = true)
    private String objetoConhecimento;
}