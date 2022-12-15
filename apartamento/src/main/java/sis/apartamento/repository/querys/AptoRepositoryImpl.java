package sis.apartamento.repository.querys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sis.apartamento.model.Apto;
import sis.apartamento.model.Apto_;
import sis.apartamento.repository.filter.AptosFilter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class AptoRepositoryImpl implements AptosRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Apto> filtrar(AptosFilter aptosFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder(); // criar criteria
        CriteriaQuery<Apto> criteria = builder.createQuery(Apto.class);
        Root<Apto> root = criteria.from(Apto.class);

        Predicate[] predicates = criarRestricoes(aptosFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Apto> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(aptosFilter));
    }

    private Predicate[] criarRestricoes(AptosFilter aptosFilter, CriteriaBuilder builder, Root<Apto> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (aptosFilter.getNumeroApto() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Apto_.NUMERO_APTO), aptosFilter.getNumeroApto()));
        }

        if (aptosFilter.getStatusApartamento() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Apto_.STATUS_APARTAMENTO), aptosFilter.getStatusApartamento()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
    private void adicionarRestricoesDePaginacao(TypedQuery<Apto> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(AptosFilter aptosFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Apto> root = criteria.from(Apto.class);

        Predicate[] predicates = criarRestricoes(aptosFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
