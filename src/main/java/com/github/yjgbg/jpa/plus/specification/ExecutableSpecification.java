package com.github.yjgbg.jpa.plus.specification;

import com.github.yjgbg.jpa.plus.specification.api.CombinedSpecification;
import com.github.yjgbg.jpa.plus.specification.api.PageReq;
import com.github.yjgbg.jpa.plus.specification.api.QueryExecutor;
import com.github.yjgbg.jpa.plus.specification.api.Sort;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.ExtensionMethod;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

@ExtensionMethod(QueryExecutor.class)
public class ExecutableSpecification<T> extends CombinedSpecification<T> {

    @Getter
    private final QueryExecutor<T> queryExecutor;
    @Getter
    @Setter
    private Sort sort = Sort.unsorted();

    public ExecutableSpecification(EntityManager entityManager, Class<T> tClass) {
        this(QueryExecutor.of(entityManager, tClass));
    }

    public ExecutableSpecification(QueryExecutor<T> queryExecutor) {
        super(Predicate.BooleanOperator.OR);
        this.queryExecutor = queryExecutor;
    }

    public Optional<T> findOne() {
        return queryExecutor.findOne(this);
    }

    public List<T> findAll() {
        return queryExecutor.findAll(this, sort);
    }

    public List<T> findAll(int page, int pageSize) {
        return queryExecutor.findAll(this, PageReq.of(page, pageSize, sort));
    }

    public Long count() {
        return queryExecutor.count(this);
    }
}
