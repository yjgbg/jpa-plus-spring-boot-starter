package com.github.yjgbg.jpa.plus.specification.api;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(staticName = "of")
public class QueryExecutor<Domain> {
    private final EntityManager entityManager;
    private final Class<Domain> domainClass;

    public static <Domain> Optional<Domain> findOne(QueryExecutor<Domain> executor,
                                                    @NotNull Specification<Domain> specification) {
        return QueryUtils.findOne(executor.entityManager, executor.domainClass, specification);
    }

    public static <Domain> List<Domain> findAll(QueryExecutor<Domain> executor,
                                                @NotNull Specification<Domain> specification, Sort sort) {
        return QueryUtils.findAll(executor.entityManager, executor.domainClass, specification, sort);
    }

    public static <Domain> List<Domain> findAll(QueryExecutor<Domain> executor,
                                                @NotNull Specification<Domain> specification, PageReq pageReq) {
        return QueryUtils.findAll(executor.entityManager, executor.domainClass, specification, pageReq);
    }

    public static <Domain> Long count(QueryExecutor<Domain> executor,
                                      @NotNull Specification<Domain> specification) {
        return QueryUtils.count(executor.entityManager, executor.domainClass, specification);
    }
}
