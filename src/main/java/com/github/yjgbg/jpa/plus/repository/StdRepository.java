package com.github.yjgbg.jpa.plus.repository;

import com.github.yjgbg.jpa.plus.specification.ExecutableSpecification;
import com.github.yjgbg.jpa.plus.specification.api.QueryExecutor;

import javax.persistence.EntityManager;

/**
 * @author yjgbg
 */
public class StdRepository<T> {
    private final QueryExecutor<T> queryExecutor;

    public StdRepository(EntityManager entityManager, Class<T> domainClass) {
        queryExecutor = QueryExecutor.of(entityManager, domainClass);
    }

    /**
     * 获取到一个可执行(find，count等函数)的Specification
     *
     * @return 可执行(find ， count等函数)的Specification
     */
    ExecutableSpecification<T> spec() {
        return new ExecutableSpecification<>(queryExecutor);
    }
}
