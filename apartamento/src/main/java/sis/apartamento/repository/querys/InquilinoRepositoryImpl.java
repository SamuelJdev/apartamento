package sis.apartamento.repository.querys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sis.apartamento.model.Inquilino;
import sis.apartamento.model.Inquilino_;
import sis.apartamento.repository.filter.InquilinoFilter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class InquilinoRepositoryImpl implements InquilinoRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Inquilino> filtrar(InquilinoFilter inquilinoFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Inquilino> criteria = builder.createQuery(Inquilino.class);
        Root<Inquilino> root = criteria.from(Inquilino.class);

        Predicate[] predicates = criarRestricoes(inquilinoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Inquilino> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(inquilinoFilter));
    }

    private Predicate[] criarRestricoes(InquilinoFilter inquilinoFilter, CriteriaBuilder builder, Root<Inquilino> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (inquilinoFilter.getNome() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Inquilino_.NOME), inquilinoFilter.getNome().toLowerCase()));
        }

        if (inquilinoFilter.getCpf() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Inquilino_.CPF), inquilinoFilter.getCpf()));
        }

        if (inquilinoFilter.getValorContraCheque() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Inquilino_.VALOR_CONTRA_CHEQUE), inquilinoFilter.getValorContraCheque()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
    private void adicionarRestricoesDePaginacao(TypedQuery<Inquilino> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(InquilinoFilter inquilinoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Inquilino> root = criteria.from(Inquilino.class);

        Predicate[] predicates = criarRestricoes(inquilinoFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
