package com.github.yjgbg.jpa.plus.repository;

import lombok.Getter;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public class JpaSpecificationRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements JpaSpecificationRepository<T> {
    @Getter
    private final Class<T> domainClass;
    // 如果为fetch，则在查询时未指定的字段为懒加载，如果为load，则在查询时未指定的字段按照在实体上指定的加载方式加载
    private static final EntityGraphType DEFAULT_ENTITY_GRAPH_TYPE = EntityGraphType.FETCH;

    public JpaSpecificationRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.domainClass = entityInformation.getJavaType();
    }

    @Override
    public List<T> findAll(Specification<T> specification,
                           @Nullable EntityGraph<T> entityGraph,
                           @NotNull Sort sort) {
        if (entityGraph == null) return findAll(specification, sort);
        val query = getQuery(specification, sort);
        query.setHint(DEFAULT_ENTITY_GRAPH_TYPE.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public Page<T> findAll(Specification<T> specification,
                           @Nullable EntityGraph<T> entityGraph,
                           @NotNull Pageable pageable) {
        if (entityGraph == null) return findAll(specification, pageable);
        val query = getQuery(specification, pageable);
        query.setHint(DEFAULT_ENTITY_GRAPH_TYPE.getKey(), entityGraph);
        return pageable.isUnpaged() ? new PageImpl<>(query.getResultList())
                : readPage(query, getDomainClass(), pageable, specification);
    }

    @Override
    public Optional<T> findOne(Specification<T> specification,
                               @Nullable EntityGraph<T> entityGraph) {
        if (entityGraph==null) return findOne(specification);
        try {
            val query = getQuery(specification, Sort.unsorted());
            query.setHint(DEFAULT_ENTITY_GRAPH_TYPE.getKey(), entityGraph);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException op) {
            return Optional.empty();
        }
    }
}
