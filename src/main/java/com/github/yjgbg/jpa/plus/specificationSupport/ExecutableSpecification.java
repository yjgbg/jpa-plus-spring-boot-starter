package com.github.yjgbg.jpa.plus.specificationSupport;

import com.github.yjgbg.jpa.plus.entitySupport.StdEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ExecutableSpecification<T extends StdEntity<T>> implements
    ChainSpecification<T, ExecutableSpecification<T>>,
    Sortable<ExecutableSpecification<T>>,
    SpecExecutor<T> {

  private final JpaSpecificationExecutorPro<T> jpaSpecificationExecutor;
  private Specification<T> specification;
  private Sort sort;

  @Override
  public Specification<T> toSpecification() {
    return specification;
  }

  @Override
  public ExecutableSpecification<T> sort(Sort sort) {
    this.sort = sort;
    return this;
  }

  @Override
  @NotNull
  public ExecutableSpecification<T> and(Specification<T> value) {
    specification = specification == null ? value : specification.and(value);
    return this;
  }

  @NotNull
  public ExecutableSpecification<T> or() {
    val that = this;
    val exe = new ExecutableSpecification<T>(getJpaSpecificationExecutor()) {
      @Override
      public Specification<T> toSpecification() {
        return that.toSpecification();
      }

      @Override
      public ExecutableSpecification<T> sort(Sort sort) {
        return that.sort(sort);
      }
    };
    specification = specification.or(exe::toPredicate);
    return exe;
  }

  @Nullable
  protected Predicate toPredicate(@NotNull Root<T> root,
      @NotNull CriteriaQuery<?> query,
      @NotNull CriteriaBuilder criteriaBuilder) {
    return specification.toPredicate(root, query, criteriaBuilder);
  }
}
