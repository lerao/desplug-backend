package br.edu.ifpe.afogados.desplugai.entity;

import br.edu.ifpe.afogados.desplugai.enums.SecretariaEducacaoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Secretaria_Educacao")
public class SecretariaEducacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SecretariaEducacaoEnum tipo;

    @Column(nullable = false, length = 2)
    private String uf;

    @Column(nullable = false)
    private String municipio;

    @Column(name = "Dominio_Oficial", nullable = false)
    private String dominioOficial;

    @Column(nullable = false)
    private Boolean ativo;
}