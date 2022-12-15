package sis.apartamento.repository.querys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sis.apartamento.model.ContraCheque;
import sis.apartamento.model.ContraCheque_;
import sis.apartamento.repository.filter.ContraChequeFilter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ContraChequeRepositoryImpl implements ContraChequeRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<ContraCheque> filtrar(ContraChequeFilter contraChequeFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<ContraCheque> criteria = builder.createQuery(ContraCheque.class);
        Root<ContraCheque> root = criteria.from(ContraCheque.class);

        Predicate[] predicates = criarRestricoes(contraChequeFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<ContraCheque> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(contraChequeFilter));
    }

    private Predicate[] criarRestricoes(ContraChequeFilter contraChequeFilter, CriteriaBuilder builder, Root<ContraCheque> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (contraChequeFilter.getDataContraCheque() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(ContraCheque_.DATA_CONTRA_CHEQUE), contraChequeFilter.getDataContraCheque()));
        }

        if (contraChequeFilter.getValorContracheque() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(ContraCheque_.VALOR_CONTRACHEQUE), contraChequeFilter.getValorContracheque()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
    private void adicionarRestricoesDePaginacao(TypedQuery<ContraCheque> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(ContraChequeFilter contraChequeFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<ContraCheque> root = criteria.from(ContraCheque.class);

        Predicate[] predicates = criarRestricoes(contraChequeFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
