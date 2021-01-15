package com.github.yjgbg.jpa.plus.specification.api;

import org.jetbrains.annotations.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@FunctionalInterface
public interface Specification<T> {
    @Nullable
    Predicate predicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);
}
