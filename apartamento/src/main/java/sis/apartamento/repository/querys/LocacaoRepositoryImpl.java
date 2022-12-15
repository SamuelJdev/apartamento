package sis.apartamento.repository.querys;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sis.apartamento.model.Locacao;
import sis.apartamento.model.Locacao_;
import sis.apartamento.repository.filter.LocacaoFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LocacaoRepositoryImpl implements LocacaoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Locacao> filtrar(LocacaoFilter locacaoFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Locacao> criteria = builder.createQuery(Locacao.class);
        Root<Locacao> root = criteria.from(Locacao.class);

        Predicate[] predicates = criarRestricoes(locacaoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Locacao> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(locacaoFilter));
    }

    private Predicate[] criarRestricoes(LocacaoFilter locacaoFilter, CriteriaBuilder builder, Root<Locacao> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(locacaoFilter.getStatusLocacao())) {
            predicates.add(builder.like(builder.lower(root.get(Locacao_.statusLocacao)), "%" + locacaoFilter.getStatusLocacao().toUpperCase() + "%"));
        }

        if (locacaoFilter.getDataEntrada() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Locacao_.DATA_ENTRADA), locacaoFilter.getDataEntrada()));
        }

        if (locacaoFilter.getDataSaida() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Locacao_.DATA_SAIDA), locacaoFilter.getDataSaida()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
    private void adicionarRestricoesDePaginacao(TypedQuery<Locacao> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(LocacaoFilter locacaoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Locacao> root = criteria.from(Locacao.class);

        Predicate[] predicates = criarRestricoes(locacaoFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
