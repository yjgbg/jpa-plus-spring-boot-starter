package com.github.yjgbg.jpa.plus.specification.support;

import com.github.yjgbg.jpa.plus.repository.JpaSpecificationRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityGraph;
import java.util.List;
import java.util.Optional;

/**
 * 查询功能，模板模式
 *
 * @param <T>
 */
public interface ExecuteSupport<T> extends Specification<T> {

    @NotNull
    JpaSpecificationRepository<T> getJpaSpecificationRepository();

    @NotNull
    Sort getSort();

    @Nullable
    EntityGraph<T> getEntityGraph();

    @NotNull
    default Optional<T> findOne() {
        return getJpaSpecificationRepository().findOne(this,
                getEntityGraph());
    }

    @NotNull
    default List<T> findAll() {
        return getJpaSpecificationRepository().findAll(this,
                getEntityGraph(), getSort());
    }

    @NotNull
    default Page<T> findAll(int page, int size) {
        return getJpaSpecificationRepository().findAll(this,
                getEntityGraph(), PageRequest.of(page, size, getSort()));
    }

    default long count() {
        return getJpaSpecificationRepository().count(this);
    }

    default boolean exist() { // 取一个看是否为空
        return !findAll(0, 1).isEmpty();
    }
}