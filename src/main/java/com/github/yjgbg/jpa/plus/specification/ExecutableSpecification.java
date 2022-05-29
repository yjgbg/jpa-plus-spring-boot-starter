package com.github.yjgbg.jpa.plus.specification;

import com.github.yjgbg.jpa.plus.repository.JpaSpecificationRepository;
import com.github.yjgbg.jpa.plus.specification.support.*;
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

@RequiredArgsConstructor
public class ExecutableSpecification<T> implements
    EntityGraphSupport<ExecutableSpecification<T>, T>,
    GetterSupport<T, ExecutableSpecification<T>>,
    ChainSupport<T, ExecutableSpecification<T>>,
    SortSupport<ExecutableSpecification<T>,T>,
    ExecuteSupport<T> {

    @Getter
    private final JpaSpecificationRepository<T> jpaSpecificationRepository;
    @Getter
    private EntityGraph<T> entityGraph;
    @Getter
    private Sort sort = Sort.unsorted();

    /**
     * 目前的最后一条变量，用于OR
     * 会被flush函数刷入到查询条件中
     */
    private Specification<T> previous;
    /**
     * 刚执行完OR，尚未执行下一发and时，flag为true，其他时候为false
     */
    private boolean orFlag = false;

    private final List<Specification<T>> specificationList = new ArrayList<>();
    private int currentSpecIndex = 0;

    @Override
    public Class<T> getDomainClass() {
        return getJpaSpecificationRepository()
            .getDomainClass();
    }

    @Override
    public ExecutableSpecification<T> sort(Sort sort) {
        this.sort = sort;
        return this;
    }

    @Override
    @NotNull
    public ExecutableSpecification<T> and(@Nullable Specification<T> value) {
        if (value==null) return this;
        if (orFlag) {
            previous = previous == null ? value : previous.or(value);
            orFlag = false;
            return this;
        }
        flush();
        previous = value;
        return this;
    }

    @Override
    public Predicate toPredicate(@NotNull Root<T> root,
                                 @NotNull CriteriaQuery<?> cq,
                                 @NotNull CriteriaBuilder cb) {
        flush();
        if (specificationList.isEmpty()) return null;
        return specificationList.stream().reduce(Specification::or)
            .map(x -> x.toPredicate(root, cq, cb))
            .orElse(null);
    }

    @NotNull
    public ExecutableSpecification<T> or() {
        flush();
        if (getCurrentSpec() != null) currentSpecIndex += 1;
        return this;
    }

    @NotNull
    public ExecutableSpecification<T> OR() {
        if (previous == null)
            throw new UnsupportedOperationException("不可以在没有前置条件的时候开启OR模式");
        orFlag = true;
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

    /**
     * 刷出previous变量
     */
    private void flush() {
        if (orFlag) throw new UnsupportedOperationException("不合法的调用，不可以在开启OR模式时关闭条件构造器");
        if (previous == null) return;
        val beforeAnd = getCurrentSpec();
        val afterAnd = beforeAnd == null ?
            previous : beforeAnd.and(previous);
        setCurrentSpec(afterAnd);
        previous = null;
    }

    private void setCurrentSpec(Specification<T> specification) {
        if (currentSpecIndex == specificationList.size())
            specificationList.add(specification);
        else specificationList.set(currentSpecIndex, specification);
    }
}
