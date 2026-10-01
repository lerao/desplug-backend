package br.edu.ifpe.afogados.desplugai.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdaptacaoPlanoInput {

    @NotNull(message = "Informe os materiais disponíveis.")
    private String materiaisDisponiveis;

    @NotNull(message = "Informe o perfil da turma.")
    private String perfilTurma;

    @NotNull(message = "Informe a habilidade de foco.")
    private String habilidadeFoco;

    @NotNull(message = "Informe as observações e instruções a serem seguidas na elaboração.")
    private String observacoesInstrucoes;
}
