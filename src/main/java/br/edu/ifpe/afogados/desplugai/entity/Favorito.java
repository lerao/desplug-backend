package br.edu.ifpe.afogados.desplugai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private Usuario idUsuario;

    @ManyToOne()
    private PlanoAula idPlano;

    @Column(nullable = false)
    private Timestamp dataRegistro;
}