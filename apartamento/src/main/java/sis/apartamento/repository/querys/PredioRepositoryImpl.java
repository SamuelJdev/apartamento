package sis.apartamento.repository.querys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sis.apartamento.model.Predio;
import sis.apartamento.model.Predio_;
import sis.apartamento.repository.filter.PredioFilter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PredioRepositoryImpl implements PredioRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Predio> filtrar(PredioFilter predioFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder(); // criar criteria
        CriteriaQuery<Predio> criteria = builder.createQuery(Predio.class);
        Root<Predio> root = criteria.from(Predio.class);// root pra pega os atributos do predio

        Predicate[] predicates = criarRestricoes(predioFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Predio> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(predioFilter));
    }

    private Predicate[] criarRestricoes(PredioFilter predioFilter, CriteriaBuilder builder, Root<Predio> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (predioFilter.getNumeroPredio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Predio_.NUMERO_PREDIO), predioFilter.getNumeroPredio()));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }
    private void adicionarRestricoesDePaginacao(TypedQuery<Predio> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(PredioFilter predioFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Predio> root = criteria.from(Predio.class);

        Predicate[] predicates = criarRestricoes(predioFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
