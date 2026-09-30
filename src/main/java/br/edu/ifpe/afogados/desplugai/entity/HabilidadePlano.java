package br.edu.ifpe.afogados.desplugai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class HabilidadePlano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_plano", nullable = false)
    private PlanoAula plano;

    @ManyToOne
    @JoinColumn(name = "id_habilidade", nullable = false)
    private HabilidadeBncc habilidade;
}
