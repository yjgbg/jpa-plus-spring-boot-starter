package com.github.yjgbg.jpa.plus.specificationSupport;

import com.github.yjgbg.jpa.plus.repository.JpaSpecificationRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
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

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ExecutableSpecification<T> implements
        EntityGraphEditor<ExecutableSpecification<T>, T>,
        GetterSupport<T, ExecutableSpecification<T>>,
        Sortable<ExecutableSpecification<T>>,
        SpecExecutor<T> {

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
        if (value==null) return this;
        val beforeAnd = getCurrentSpec();
        val afterAnd = beforeAnd == null ?
                value : beforeAnd.and(value);
        setCurrentSpec(currentSpecIndex, afterAnd);
        return this;
    }

    @Override
    public Predicate toPredicate(@NotNull Root<T> root,
                                 @NotNull CriteriaQuery<?> criteriaQuery,
                                 @NotNull CriteriaBuilder criteriaBuilder) {
        if (specificationList.isEmpty()) {
            return null;
        }
        return specificationList.stream().reduce(Specification::or)
                .map(x -> x.toPredicate(root, criteriaQuery, criteriaBuilder))
                .orElse(null);
    }

    @NotNull
    public ExecutableSpecification<T> or() {
        if (getCurrentSpec()!=null) {
            currentSpecIndex += 1;
        }
        return this;
    }

    @Override
    public ExecutableSpecification<T> setEntityGraph(EntityGraph<T> entityGraph) {
        this.entityGraph = entityGraph;
        return this;
    }

    private Specification<T> getCurrentSpec() {
        if (currentSpecIndex >= specificationList.size()) {
            return null;
        } else {
            return specificationList.get(currentSpecIndex);
        }
    }

    private void setCurrentSpec(int index, Specification<T> specification) {
        specificationList.set(index, specification);
    }
}
