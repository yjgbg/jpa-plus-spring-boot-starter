package com.github.yjgbg.jpa.plus.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityGraph;
import java.util.List;
import java.util.Optional;

/**
 * 在Repository中对EntityGraph进行支持
 *
 * @param <T>
 * @author yjgbg
 */
@NoRepositoryBean
public interface JpaSpecificationRepository<T> extends JpaSpecificationExecutor<T> {

    /**
     * 获取T的类型
     *
     * @return 在构造EntityGraph时需要使用。也许还会有很多需要使用的地方
     */
    Class<T> getDomainClass();

    List<T> findAll(@Nullable Specification<T> specification,
                    @Nullable EntityGraph<T> entityGraph,
                    @NotNull Sort sort);

    Page<T> findAll(@Nullable Specification<T> specification,
                    @Nullable EntityGraph<T> entityGraph,
                    @NotNull Pageable pageable);

    Optional<T> findOne(@Nullable Specification<T> specification,
                        @Nullable EntityGraph<T> entityGraph);
}
