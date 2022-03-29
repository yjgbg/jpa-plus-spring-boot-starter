package com.github.yjgbg.jpa.plus.specification;

import com.github.yjgbg.jpa.plus.repository.JpaSpecificationRepository;
import com.github.yjgbg.jpa.plus.specification.support.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityGraph;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ExecutableSpecification<T> implements
        EntityGraphSupport<ExecutableSpecification<T>, T>,
        GetterSupport<T, ExecutableSpecification<T>>,
        ChainSupport<T, ExecutableSpecification<T>>,
        SortSupport<ExecutableSpecification<T>>,
        ExecuteSupport<T> {

    @Getter
    private final JpaSpecificationRepository<T> jpaSpecificationRepository;
    @Getter
    private EntityGraph<T> entityGraph;
    @Getter
    private Sort sort = Sort.unsorted();

    @Override
    public Class<T> getDomainClass() {
        return getJpaSpecificationRepository()
                .getDomainClass();
    }

    private final List<Specification<T>> specificationList = new ArrayList<>();
    private int currentSpecIndex = 0;

    @Override
    public ExecutableSpecification<T> sort(Sort sort) {
        this.sort = sort;
        return this;
    }

    @Override
    @NotNull
    public ExecutableSpecification<T> and(@Nullable Specification<T> value) {
        if (value == null) return this;
        final var beforeAnd = getCurrentSpec();
        final var afterAnd = beforeAnd == null ?
                value : beforeAnd.and(value);
        setCurrentSpec(afterAnd);
        return this;
    }

    @Override
    public Predicate toPredicate(@NotNull Root<T> root,
                                 @NotNull CriteriaQuery<?> cq,
                                 @NotNull CriteriaBuilder cb) {
        if (specificationList.isEmpty()) return null;
        return specificationList.stream().reduce(Specification::or)
                .map(x -> x.toPredicate(root, cq, cb))
                .orElse(null);
    }

    @NotNull
    public ExecutableSpecification<T> or() {
        if (getCurrentSpec() != null) currentSpecIndex += 1;
        return this;
    }

    @Override
    public ExecutableSpecification<T> setEntityGraph(EntityGraph<T> entityGraph) {
        this.entityGraph = entityGraph;
        return this;
    }

    private Specification<T> getCurrentSpec() {
        if (currentSpecIndex == specificationList.size()) return null;
        return specificationList.get(currentSpecIndex);

    }

    private void setCurrentSpec(Specification<T> specification) {
        if (currentSpecIndex == specificationList.size())
            specificationList.add(specification);
        else specificationList.set(currentSpecIndex, specification);
    }
}
