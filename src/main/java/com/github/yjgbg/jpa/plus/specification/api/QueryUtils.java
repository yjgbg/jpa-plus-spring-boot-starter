package com.github.yjgbg.jpa.plus.specification.api;

import com.github.yjgbg.jpa.plus.utils.PathUtils;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class QueryUtils {
    protected static <T> Optional<T> findOne(@NotNull EntityManager em, @NotNull Class<T> domainClass,
                                             @NotNull Specification<T> specification) {
        try {
            val query = typedQuery(em, domainClass, specification, Sort.unsorted());
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException op) {
            return Optional.empty();
        }
    }

    protected static <T> List<T> findAll(@NotNull EntityManager em, @NotNull Class<T> domainClass,
                                         @NotNull Specification<T> specification, @NotNull Sort sort) {
        val query = typedQuery(em, domainClass, specification, sort);
        return query.getResultList();
    }

    protected static <T> Long count(@NotNull EntityManager em, @NotNull Class<T> domainClass,
                                    @NotNull Specification<T> specification) {
        val query = countQuery(em, domainClass, specification);
        return query.getSingleResult();
    }

    protected static <T> List<T> findAll(@NotNull EntityManager em, @NotNull Class<T> domainClass,
                                         @NotNull Specification<T> specification, @NotNull PageReq pageReq) {
        val query = typedQuery(em, domainClass, specification, pageReq.getSort());
        query.setFirstResult(pageReq.getOffset());
        query.setMaxResults(pageReq.getPageSize());
        return query.getResultList();
    }


    private static <T> TypedQuery<T> typedQuery(@NotNull EntityManager em,
                                                @NotNull Class<T> domainClass,
                                                @NotNull Specification<T> specification,
                                                @NotNull Sort sort) {
        val cb = em.getCriteriaBuilder();
        val query = cb.createQuery(domainClass);
        val root = query.from(domainClass);
        val predicate = specification.predicate(root, query, cb);
        if (predicate != null) query.where(predicate);
        query.select(root);
        setOrder(query, sort, root, cb);
        return em.createQuery(query);
    }

    private static <T> TypedQuery<Long> countQuery(@NotNull EntityManager em,
                                                   @NotNull Class<T> domainClass,
                                                   @NotNull Specification<T> specification) {
        val cb = em.getCriteriaBuilder();
        val query = cb.createQuery(Long.class);
        val root = query.from(domainClass);
        val predicate = specification.predicate(root, query, cb);
        if (predicate != null) query.where(predicate);
        query.select(cb.count(root));
        query.orderBy(Collections.emptyList());
        return em.createQuery(query);
    }

    private static <A, B> void setOrder(CriteriaQuery<B> query, Sort sort,
                                        @NotNull Root<A> from, @NotNull CriteriaBuilder cb) {
        if (!sort.isSorted()) return;
        val jpaOrders = sort.getOrders().stream()
                .map(order -> {
                    val path = PathUtils.str2Path(from, order.getProperty());
                    return order.isAscending() ? cb.asc(path) : cb.desc(path);
                })
                .collect(Collectors.toList());
        query.orderBy(jpaOrders);
    }
}
