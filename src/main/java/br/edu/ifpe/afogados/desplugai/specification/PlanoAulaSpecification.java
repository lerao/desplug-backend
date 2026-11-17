package br.edu.ifpe.afogados.desplugai.specification;

import br.edu.ifpe.afogados.desplugai.entity.Favorito;
import br.edu.ifpe.afogados.desplugai.entity.HabilidadeBncc;
import br.edu.ifpe.afogados.desplugai.entity.HabilidadePlano;
import br.edu.ifpe.afogados.desplugai.entity.PlanoAula;
import br.edu.ifpe.afogados.desplugai.entity.Usuario;
import br.edu.ifpe.afogados.desplugai.enums.EtapaEnsinoEnum;
import br.edu.ifpe.afogados.desplugai.enums.StatusPublicacaoEnum;
import br.edu.ifpe.afogados.desplugai.enums.TipoAtividadeEnum;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public class PlanoAulaSpecification {

    public static Specification<PlanoAula> comAutor(Usuario autor) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("autor"),
                        autor
                );
    }

    public static Specification<PlanoAula> comTitulo(String titulo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("titulo")),
                        "%" + titulo.toLowerCase() + "%"
                );
    }

    public static Specification<PlanoAula> comResumo(String resumo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("resumo")),
                        "%" + resumo.toLowerCase() + "%"
                );
    }

    public static Specification<PlanoAula> comTipoAtividade(TipoAtividadeEnum tipoAtividade) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("tipoAtividade"),
                        tipoAtividade
                );
    }

    public static Specification<PlanoAula> comEtapaEnsino(
            EtapaEnsinoEnum etapaEnsino
    ) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("etapaEnsino"),
                        etapaEnsino
                );
    }

    public static Specification<PlanoAula> comAnosIndicados(String anosIndicados) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("anosIndicados"),
                        anosIndicados
                );
    }

    public static Specification<PlanoAula> comDuracao(String duracao) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("duracao"),
                        duracao
                );
    }

    public static Specification<PlanoAula> comComponentesCurriculares(String componentes) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(
                                root.get("componentesCurriculares")
                        ),
                        "%" + componentes.toLowerCase() + "%"
                );
    }

    public static Specification<PlanoAula> comMateriaisNecessarios(
            String materiaisNecessarios
    ) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(
                                root.get("materiaisNecessarios")
                        ),
                        "%" + materiaisNecessarios.toLowerCase() + "%"
                );
    }

    public static Specification<PlanoAula> comMetodologia(String metodologia) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(
                                root.get("metodologia")
                        ),
                        "%" + metodologia.toLowerCase() + "%"
                );
    }

    public static Specification<PlanoAula> comHabilidade(String codigoHabilidade) {

        return (root, query, criteriaBuilder) -> {

            query.distinct(true);

            Join<PlanoAula, HabilidadePlano> habilidades =
                    root.join("habilidadesPlano", JoinType.INNER);

            Join<HabilidadePlano, HabilidadeBncc> habilidadeBncc =
                    habilidades.join("habilidade", JoinType.INNER);

            return criteriaBuilder.equal(
                    habilidadeBncc.get("codigo"),
                    codigoHabilidade
            );
        };
    }

    public static Specification<PlanoAula> comStatusPublicacao(
            StatusPublicacaoEnum statusPublicacao
    ) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("status"),
                        statusPublicacao
                );
    }

    public static Specification<PlanoAula> comIsGeradoIA(Boolean isGeradoIA) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("isGeradoIA"),
                        isGeradoIA
                );
    }


    public static Specification<PlanoAula> comOrdenacaoMaisFavoritos() {
        return (root, query, criteriaBuilder) -> {

            if (query.getResultType() == Long.class) {
                return criteriaBuilder.conjunction();
            }

            Subquery<Long> subquery = query.subquery(Long.class);

            Root<Favorito> favorito = subquery.from(Favorito.class);

            subquery.select(criteriaBuilder.count(favorito));

            subquery.where(
                    criteriaBuilder.equal(
                            favorito.get("planoAula"),
                            root
                    )
            );

            query.orderBy(
                    criteriaBuilder.desc(subquery),
                    criteriaBuilder.desc(root.get("id"))
            );

            return criteriaBuilder.conjunction();
        };
    }


    public static Specification<PlanoAula> comOrdenacaoMaisAdaptados() {
        return (root, query, criteriaBuilder) -> {

            if (query.getResultType() == Long.class) {
                return criteriaBuilder.conjunction();
            }

            Subquery<Long> subquery = query.subquery(Long.class);

            Root<PlanoAula> adaptacaoRoot =
                    subquery.from(PlanoAula.class);

            subquery.select(
                    criteriaBuilder.count(adaptacaoRoot)
            );

            subquery.where(
                    criteriaBuilder.equal(
                            adaptacaoRoot.get("planoOrigem"),
                            root
                    )
            );

            query.orderBy(
                    criteriaBuilder.desc(subquery),
                    criteriaBuilder.desc(root.get("id"))
            );

            return criteriaBuilder.conjunction();
        };
    }

}
