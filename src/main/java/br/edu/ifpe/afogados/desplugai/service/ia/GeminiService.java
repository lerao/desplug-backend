package br.edu.ifpe.afogados.desplugai.service.ia;

import br.edu.ifpe.afogados.desplugai.dto.AdaptacaoPlanoInput;
import br.edu.ifpe.afogados.desplugai.dto.PlanoAulaDTO;
import br.edu.ifpe.afogados.desplugai.dto.PlanoAulaIAResponseDTO;
import br.edu.ifpe.afogados.desplugai.enums.EtapaEnsinoEnum;
import br.edu.ifpe.afogados.desplugai.enums.TipoAtividadeEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import tools.jackson.databind.json.JsonMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Schema;
import com.google.genai.types.Type;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private final Client client;
    private final JsonMapper jsonMapper;
    private final String model;

    public GeminiService(
            @Value("${gemini.api.key}") String apiKey,
            @Value("${gemini.model}") String model,
            JsonMapper jsonMapper
    ) {
        this.client = Client
            .builder()
                .apiKey(apiKey)
                .build();

        this.jsonMapper = jsonMapper;
        this.model = model;
    }

    public PlanoAulaIAResponseDTO adaptarPlano(
            PlanoAulaDTO planoBase,
            AdaptacaoPlanoInput input
    ) {

        String prompt = montarPrompt(planoBase, input);

        GenerateContentConfig config =
                GenerateContentConfig.builder()
                        .responseMimeType("application/json")
                        .responseSchema(criarSchemaPlanoAula())
                        .build();

        GenerateContentResponse response =
                client.models.generateContent(
                        model,
                        prompt,
                        config
                );

        System.out.println("RESPOSTA GEMINI:");
        System.out.println(response.text());

        try {
            return jsonMapper.readValue(
                    response.text(),
                    PlanoAulaIAResponseDTO.class
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao converter resposta do Gemini.",
                    e
            );
        }
    }

    private Schema criarSchemaPlanoAula() {
        Map<String, Schema> properties = new LinkedHashMap<>();

        properties.put(
                "titulo",
                Schema.builder()
                        .type(Type.Known.STRING)
                        .description("Título do plano de aula adaptado.")
                        .build()
        );

        properties.put(
                "resumo",
                Schema.builder()
                        .type(Type.Known.STRING)
                        .description("Resumo do plano de aula adaptado.")
                        .build()
        );

        properties.put(
                "tipoAtividade",
                criarSchemaEnum(TipoAtividadeEnum.class)
        );

        properties.put(
                "etapaEnsino",
                criarSchemaEnum(EtapaEnsinoEnum.class)
        );

        properties.put(
                "anosIndicados",
                Schema.builder()
                        .type(Type.Known.STRING)
                        .description("Anos escolares indicados.")
                        .build()
        );

        properties.put(
                "duracao",
                Schema.builder()
                        .type(Type.Known.STRING)
                        .description("Duração estimada da atividade.")
                        .build()
        );

        properties.put(
                "componentesCurriculares",
                Schema.builder()
                        .type(Type.Known.STRING)
                        .description("Componentes curriculares envolvidos.")
                        .build()
        );

        properties.put(
                "materiaisNecessarios",
                Schema.builder()
                        .type(Type.Known.STRING)
                        .description("Materiais necessários.")
                        .build()
        );

        properties.put(
                "metodologia",
                Schema.builder()
                        .type(Type.Known.STRING)
                        .description("Metodologia detalhada da aula.")
                        .build()
        );

        properties.put(
                "criteriosAvaliacao",
                Schema.builder()
                        .type(Type.Known.STRING)
                        .description("Critérios de avaliação.")
                        .build()
        );

        properties.put(
                "habilidades",
                Schema.builder()
                        .type(Type.Known.ARRAY)
                        .description("Habilidades sugeridas para o plano.")
                        .items(criarSchemaHabilidade())
                        .build()
        );

        return Schema.builder()
                .type(Type.Known.OBJECT)
                .properties(properties)
                .required(List.of(
                        "titulo",
                        "resumo",
                        "tipoAtividade",
                        "etapaEnsino",
                        "anosIndicados",
                        "duracao",
                        "componentesCurriculares",
                        "materiaisNecessarios",
                        "metodologia",
                        "criteriosAvaliacao",
                        "habilidades"
                ))
                .build();
    }

    private Schema criarSchemaEnum(Class<? extends Enum<?>> enumClass) {

        List<String> valores = Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .toList();

        return Schema.builder()
                .type(Type.Known.STRING)
                .format("enum")
                .enum_(valores)
                .build();
    }

    private Schema criarSchemaHabilidade() {

        return Schema.builder()
                .type(Type.Known.OBJECT)
                .properties(Map.of(
                        "codigo",
                        Schema.builder()
                                .type(Type.Known.STRING)
                                .description(
                                        "Código da habilidade sugerida, preferencialmente no padrão BNCC."
                                )
                                .build(),

                        "descricao",
                        Schema.builder()
                                .type(Type.Known.STRING)
                                .description(
                                        "Descrição da habilidade sugerida."
                                )
                                .build()
                ))
                .required(Arrays.asList(
                        "codigo",
                        "descricao"
                ))
                .build();
    }

    private String montarPrompt(PlanoAulaDTO planoBase, AdaptacaoPlanoInput input) {

        return """
                Você é um assistente especializado na criação e adaptação
                de planos de aula.

                Sua tarefa é adaptar o plano de aula fornecido considerando
                o contexto informado pelo professor.

                REGRAS:
                - Preserve o objetivo pedagógico principal do plano original.
                - Adapte a metodologia ao perfil da turma.
                - Considere os materiais disponíveis.
                - Considere a habilidade foco informada.
                - Siga as observações e instruções do professor.
                - Não invente informações desnecessárias.
                - Mantenha a coerência entre objetivo, metodologia e avaliação.
                - Retorne somente os campos definidos no schema.
                - Use a linguagem como se fosse o próprio professor escrevendo seu próprio plano, não mencione o professor com uma terceira pessoa.
                - Para cada habilidade da BNCC-Computação sugerida, retorne OBRIGATORIAMENTE código.
                
                PLANO DE AULA BASE:

                Título: %s

                Resumo: %s

                Tipo de atividade: %s

                Etapa de ensino: %s

                Anos indicados: %s

                Duração: %s

                Componentes curriculares: %s

                Materiais necessários: %s

                Metodologia: %s

                Critérios de avaliação: %s


                CONTEXTO DA ADAPTAÇÃO:

                Materiais disponíveis:
                %s

                Perfil da turma:
                %s

                Habilidade foco:
                %s

                Observações e instruções:
                %s
                """.formatted(
                planoBase.getTitulo(),
                planoBase.getResumo(),
                planoBase.getTipoAtividade(),
                planoBase.getEtapaEnsino(),
                planoBase.getAnosIndicados(),
                planoBase.getDuracao(),
                planoBase.getComponentesCurriculares(),
                planoBase.getMateriaisNecessarios(),
                planoBase.getMetodologia(),
                planoBase.getCriteriosAvaliacao(),
                input.getMateriaisDisponiveis(),
                input.getPerfilTurma(),
                input.getHabilidadeFoco(),
                input.getObservacoesInstrucoes()
        );
    }
}
