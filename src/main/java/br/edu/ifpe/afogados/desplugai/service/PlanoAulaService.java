package br.edu.ifpe.afogados.desplugai.service;

import br.edu.ifpe.afogados.desplugai.dto.*;
import br.edu.ifpe.afogados.desplugai.entity.*;
import br.edu.ifpe.afogados.desplugai.enums.PlanoAulaOrdenacaoEnum;
import br.edu.ifpe.afogados.desplugai.enums.StatusPublicacaoEnum;
import br.edu.ifpe.afogados.desplugai.mapper.PlanoAulaMapper;
import br.edu.ifpe.afogados.desplugai.repository.*;
import br.edu.ifpe.afogados.desplugai.specification.PlanoAulaSpecification;
import jakarta.transaction.Transactional;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PlanoAulaService {

    private final PlanoAulaRepository planoAulaRepository;
    private final UsuarioRepository usuarioRepository;
    private final HabilidadeBnccRepository habilidadeBnccRepository;
    private final HabilidadePlanoRepository habilidadePlanoRepository;
    private final FavoritoRepository favoritoRepository;
    private final ProfessorAfiliacaoRepository professorAfiliacaoRepository;
    private final PlanoAulaMapper planoAulaMapper;


    public PlanoAulaService(
            PlanoAulaRepository planoAulaRepository,
            UsuarioRepository usuarioRepository,
            HabilidadeBnccRepository habilidadeBnccRepository,
            HabilidadePlanoRepository habilidadePlanoRepository,
            FavoritoRepository favoritoRepository,
            ProfessorAfiliacaoRepository professorAfiliacaoRepository,
            PlanoAulaMapper planoAulaMapper
    ) {
        this.planoAulaRepository = planoAulaRepository;
        this.usuarioRepository = usuarioRepository;
        this.habilidadeBnccRepository = habilidadeBnccRepository;
        this.habilidadePlanoRepository = habilidadePlanoRepository;
        this.favoritoRepository = favoritoRepository;
        this.professorAfiliacaoRepository = professorAfiliacaoRepository;
        this.planoAulaMapper = planoAulaMapper;
    }

    @Transactional
    public PlanoAulaDTO salvarPlanoAula(PlanoAulaDTO planoAulaDTO) {
        Usuario autorPlano  = usuarioRepository
                .findById(planoAulaDTO.getAutor().getId())
                .orElseThrow(() -> new RuntimeException("Autor inválido. O Professor não foi encontrado."));

        PlanoAula planoOrigem = null;

        if(planoAulaDTO.getPlanoOrigem() != null) {
            planoOrigem = planoAulaRepository
                    .findById(planoAulaDTO.getPlanoOrigem().getId())
                    .orElseThrow(() -> new RuntimeException("Plano de origem não encontrado."));

        }

        List<HabilidadeBncc> habilidadesBncc = new ArrayList<>();

        planoAulaDTO.getHabilidadesPlano().forEach(habilidade -> {
            if(habilidade.getHabilidade() == null) {
                throw new RuntimeException("Habilidade BNCC inválida.");
            } else {
                HabilidadeBncc habilidadeBncc = habilidadeBnccRepository
                        .findById(habilidade.getHabilidade().getId())
                        .orElseThrow(() -> new RuntimeException("A habilidade não foi encontrada. Id: " + habilidade.getHabilidade().getId()));
                habilidadesBncc.add(habilidadeBncc);
            }
        });

        PlanoAula planoAula = planoAulaMapper.toEntity(planoAulaDTO);
        planoAula.setDataCriacao(Timestamp.from(Instant.now()));
        planoAula.setStatusPublicacao(StatusPublicacaoEnum.PENDENTE_MODERACAO);
        planoAula.setVisualizacoes(0);
        planoAula.setAutor(autorPlano);
        planoAula.setPlanoOrigem(planoOrigem);

        PlanoAula planoAulaSalvo = planoAulaRepository.save(planoAula);

        List<HabilidadePlano> habilidadesPlano = new ArrayList<>();
        for (HabilidadeBncc habilidade : habilidadesBncc) {
            HabilidadePlano novoItem = new HabilidadePlano();
            novoItem.setPlano(planoAulaSalvo);
            novoItem.setHabilidade(habilidade);
            habilidadesPlano.add(novoItem);
        }
        habilidadePlanoRepository.saveAll(habilidadesPlano);

        return planoAulaMapper.toDto(planoAulaSalvo);
    }

    public Page<PlanoAulaDTO> listarPlanosAula(Pageable pageable) {
        Page<PlanoAula> listaPlanosAula = planoAulaRepository.findAll(pageable);

        List<Long> ids = listaPlanosAula.getContent().stream()
                .map(PlanoAula::getId)
                .toList();

        Map<Long, Long> favoritosPorPlano = new HashMap<>();
        if (!ids.isEmpty()) {
            for (Object[] row : favoritoRepository.contarPorPlanoIds(ids)) {
                Long planoId = (Long) row[0];
                Long total = (Long) row[1];
                favoritosPorPlano.put(planoId, total);
            }
        }

        return listaPlanosAula.map(plano -> {
            PlanoAulaDTO dto = planoAulaMapper.toDto(plano);
            dto.setTotalFavoritos(favoritosPorPlano.getOrDefault(plano.getId(), 0L));
            return dto;
        });
    }

    public PlanoAulaDTO buscarPlanoAula(Long id) {
        PlanoAula planoAula = planoAulaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Plano de aula não encontrado"));

        PlanoAulaDTO planoAulaDTO = planoAulaMapper.toDto(planoAula);
        Long totalFavoritos = favoritoRepository.contarPorPlanoId(planoAula.getId());
        planoAulaDTO.setTotalFavoritos(totalFavoritos != null ? totalFavoritos : 0L);

        return planoAulaDTO;
    }

    public PlanoAulaDTO atualizarPlanoAula(Long id, PlanoAulaDTO planoAulaDTO) {
        PlanoAula planoAulaCadastrado = planoAulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano de aula não encontrado"));

        PlanoAula planoOrigem = null;

        if(planoAulaDTO.getPlanoOrigem() != null) {
            planoOrigem = planoAulaRepository
                    .findById(planoAulaDTO.getPlanoOrigem().getId())
                    .orElseThrow(() -> new RuntimeException("Plano de origem não encontrado."));

        }

        planoAulaCadastrado.getHabilidadesPlano().clear();

        for(HabilidadePlanoDTO habilidadePlanoDTO: planoAulaDTO.getHabilidadesPlano()) {
            if(habilidadePlanoDTO.getHabilidade() == null) {
                throw new RuntimeException("Habilidade BNCC inválida.");
            } else {
                HabilidadeBncc habilidadeBncc = habilidadeBnccRepository
                        .findById(habilidadePlanoDTO.getHabilidade().getId())
                        .orElseThrow(() -> new RuntimeException("A habilidade não foi encontrada. Id: " + habilidadePlanoDTO.getHabilidade().getId()));

                HabilidadePlano habilidadePlano = new HabilidadePlano();
                habilidadePlano.setPlano(planoAulaCadastrado);
                habilidadePlano.setHabilidade(habilidadeBncc);
                planoAulaCadastrado.getHabilidadesPlano().add(habilidadePlano);
            }
        }

        planoAulaCadastrado.setTitulo(planoAulaDTO.getTitulo());
        planoAulaCadastrado.setResumo(planoAulaDTO.getResumo());
        planoAulaCadastrado.setTipoAtividade(planoAulaDTO.getTipoAtividade());
        planoAulaCadastrado.setEtapaEnsino(planoAulaDTO.getEtapaEnsino());
        planoAulaCadastrado.setAnosIndicados(planoAulaDTO.getAnosIndicados());
        planoAulaCadastrado.setDuracao(planoAulaDTO.getDuracao());
        planoAulaCadastrado.setComponentesCurriculares(planoAulaDTO.getComponentesCurriculares());
        planoAulaCadastrado.setMateriaisNecessarios(planoAulaDTO.getMateriaisNecessarios());
        planoAulaCadastrado.setMetodologia(planoAulaDTO.getMetodologia());
        planoAulaCadastrado.setCriteriosAvaliacao(planoAulaDTO.getCriteriosAvaliacao());
        planoAulaCadastrado.setImagemCapa(planoAulaDTO.getImagemCapa());
        planoAulaCadastrado.setStatusPublicacao(StatusPublicacaoEnum.PENDENTE_MODERACAO);
        planoAulaCadastrado.setIsGeradoIA(planoAulaDTO.getIsGeradoIA());
        planoAulaCadastrado.setIsDerivado(planoAulaDTO.getIsDerivado());
        planoAulaCadastrado.setPlanoOrigem(planoOrigem);
        planoAulaCadastrado.setDataAtualizacao(Timestamp.from(Instant.now()));

        planoAulaCadastrado = planoAulaRepository.save(planoAulaCadastrado);
        return planoAulaMapper.toDto(planoAulaCadastrado);
    }

    public ApiResponseDTO deletarPlanoAula(Long id) {
        PlanoAula planoAulaCadastrado = planoAulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano de aula não encontrado"));

        planoAulaRepository.delete(planoAulaCadastrado);

        return new ApiResponseDTO(
                200,
                "Plano de aula deletado com sucesso"
        );
    }


    public Page<PlanoAulaDTO> listarPlanosAulaAutor(Long autorId, Pageable pageable) {
        Usuario autor = usuarioRepository.findById(autorId)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        Page<PlanoAula> planosAulaUsuario = planoAulaRepository.findByAutor(autor, pageable);

        return planosAulaUsuario.map(planoAulaMapper::toDto);

    }

    public Page<PlanoAulaDTO> listarPlanosAulaFiltrados(
            PlanoAulaFiltroDTO filtros,
            Pageable pageable) {

        Specification<PlanoAula> specification =
                (root, query, cb) -> cb.conjunction();

        if(filtros.getIdAutor() != null) {
            Usuario autor = usuarioRepository.findById(filtros.getIdAutor())
                    .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

            specification = specification.and(
                    PlanoAulaSpecification.comAutor(autor)
            );
        }

        if (filtros.getTitulo() != null &&
                !filtros.getTitulo().isBlank()) {

            specification = specification.and(
                    PlanoAulaSpecification.comTitulo(
                            filtros.getTitulo()
                    )
            );
        }

        if (filtros.getResumo() != null &&
                !filtros.getResumo().isBlank()) {

            specification = specification.and(
                    PlanoAulaSpecification.comResumo(
                            filtros.getResumo()
                    )
            );
        }

        if (filtros.getTipoAtividade() != null) {
            specification = specification.and(
                    PlanoAulaSpecification.comTipoAtividade(
                            filtros.getTipoAtividade()
                    )
            );
        }

        if (filtros.getEtapaEnsino() != null) {
            specification = specification.and(
                    PlanoAulaSpecification.comEtapaEnsino(
                            filtros.getEtapaEnsino()
                    )
            );
        }

        if (filtros.getAnosIndicados() != null &&
                !filtros.getAnosIndicados().isBlank()) {

            specification = specification.and(
                    PlanoAulaSpecification.comAnosIndicados(
                            filtros.getAnosIndicados()
                    )
            );
        }

        if (filtros.getComDuracao() != null &&
                !filtros.getComDuracao().isBlank()) {

            specification = specification.and(
                    PlanoAulaSpecification.comDuracao(
                            filtros.getComDuracao()
                    )
            );
        }

        if (filtros.getComponentesCurriculares() != null &&
                !filtros.getComponentesCurriculares().isBlank()) {

            specification = specification.and(
                    PlanoAulaSpecification.comComponentesCurriculares(
                            filtros.getComponentesCurriculares()
                    )
            );
        }

        if (filtros.getMateriaisNecessarios() != null &&
                !filtros.getMateriaisNecessarios().isBlank()) {

            specification = specification.and(
                    PlanoAulaSpecification.comMateriaisNecessarios(
                            filtros.getMateriaisNecessarios()
                    )
            );
        }

        if (filtros.getMetodologia() != null &&
                !filtros.getMetodologia().isBlank()) {

            specification = specification.and(
                    PlanoAulaSpecification.comMetodologia(
                            filtros.getMetodologia()
                    )
            );
        }

        if (filtros.getCodigoHabilidade() != null &&
                !filtros.getCodigoHabilidade().isBlank()) {

            specification = specification.and(
                    PlanoAulaSpecification.comHabilidade(
                            filtros.getCodigoHabilidade()
                    )
            );
        }

        if (filtros.getStatusPublicacao() != null) {
            specification = specification.and(
                    PlanoAulaSpecification.comStatusPublicacao(
                            filtros.getStatusPublicacao()
                    )
            );
        }

        if (filtros.getIsGeradoIA() != null) {
            specification = specification.and(
                    PlanoAulaSpecification.comIsGeradoIA(
                            filtros.getIsGeradoIA()
                    )
            );
        }

        if(filtros.getOrderBy() != null) {
            if(PlanoAulaOrdenacaoEnum.MAIS_RECENTES == filtros.getOrderBy()) {
                Sort sort = Sort.by(
                        Sort.Order.desc("dataCriacao"),
                        Sort.Order.desc("id")
                );

                pageable = PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        sort
                );
            } else if(PlanoAulaOrdenacaoEnum.MAIS_FAVORITOS == filtros.getOrderBy()) {
                specification = specification.and(
                        PlanoAulaSpecification.comOrdenacaoMaisFavoritos()
                );
            } else if(PlanoAulaOrdenacaoEnum.MAIS_ADAPTADOS == filtros.getOrderBy()) {
                specification = specification.and(
                        PlanoAulaSpecification.comOrdenacaoMaisAdaptados()
                );
            }
        }

        Page<PlanoAula> resultado =
                planoAulaRepository.findAll(specification, pageable);

        return resultado.map(planoAulaMapper::toDto);
    }

    public byte[] gerarPdf(Long idPlano) {
        PlanoAula planoAula = planoAulaRepository
                .findById(idPlano)
                .orElseThrow(() -> new RuntimeException("Plano de aula não encontrado com ID: " + idPlano));

        PlanoAulaReportDTO dto = new PlanoAulaReportDTO();

        Optional<ProfessorAfiliacao> afiliacao = professorAfiliacaoRepository
                .findByProfessor_Id(planoAula.getAutor().getId());

        afiliacao.ifPresent(professorAfiliacao -> dto.setNomeInstituicao(
                professorAfiliacao.getSecretaria().getNome()
        ));

        dto.setNomeProfessor(planoAula.getAutor().getNome());
        dto.setDataPlano(planoAula.getDataCriacao().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dto.setDuracao(planoAula.getDuracao());
        dto.setComponentesCurriculares(planoAula.getComponentesCurriculares());
        dto.setAnosIndicados(planoAula.getAnosIndicados());

        List<String> habilidades = new ArrayList<>();
        for(HabilidadePlano habilidade : planoAula.getHabilidadesPlano()) {
            habilidades.add(habilidade.getHabilidade().getCodigo());
        }
        dto.setHabilidadeBncc(habilidades);
        dto.setTituloPlano(planoAula.getTitulo());
        dto.setResumoPlano(planoAula.getResumo());
        dto.setMetodologia(planoAula.getMetodologia());
        dto.setMateriaisNecessarios(planoAula.getMateriaisNecessarios());
        dto.setCriteriosAvaliacao(planoAula.getCriteriosAvaliacao());

        JRBeanCollectionDataSource dataSource =
                new JRBeanCollectionDataSource(Collections.singletonList(dto));

        try (InputStream inputStream = getClass().getResourceAsStream("/reports/planoAula.jrxml")) {
            if (inputStream == null) {
                throw new FileNotFoundException("Template '/reports/planoAula.jrxml' não encontrado.");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Parâmetros do relatório (pode ser usado para dados gerais como logotipo)
            Map<String, Object> parametros = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parametros,
                    dataSource
            );

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF do plano de aula", e);
        }
    }

}