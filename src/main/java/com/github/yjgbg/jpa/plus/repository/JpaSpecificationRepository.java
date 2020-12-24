package com.github.yjgbg.jpa.plus.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import javax.persistence.EntityGraph;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface JpaSpecificationRepository<T> extends JpaSpecificationExecutor<T> {

    Class<T> getDomainClass();
    List<T> findAll(Specification<T> specification,
                    EntityGraphType entityGraphType,
                    EntityGraph<T> entityGraph,
                    Sort sort);
    Page<T> findAll(Specification<T> specification,
                    EntityGraphType entityGraphType,
                    EntityGraph<T> entityGraph,
                    Pageable pageable);
    Optional<T> findOne(Specification<T> specification,
                        EntityGraphType entityGraphType,
                        EntityGraph<T> entityGraph);
}
