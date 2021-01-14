package com.github.yjgbg.jpa.plus.specification.api;

import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@FunctionalInterface
public interface Specification<T> {

    static <T> Specification<T> not(Specification<T> spec) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.not(spec.toPredicate(root, query, criteriaBuilder));
    }

    static <T> Specification<T> and(@Nullable Specification<T> that, @Nullable Specification<T> other) {
        return composed(that, other, ComposedType.AND);
    }

    static <T> Specification<T> or(Specification<T> that, Specification<T> other) {
        return composed(that, other, ComposedType.OR);
    }

    static <T> Specification<T> composed(Specification<T> one, Specification<T> other, @NotNull ComposedType composedType) {
        return (root, query, criteriaBuilder) -> {
            val onePredicate = one == null ? null : one.toPredicate(root, query, criteriaBuilder);
            val otherPredicate = other == null ? null : other.toPredicate(root, query, criteriaBuilder);
            if (onePredicate == null) return otherPredicate;
            if (otherPredicate == null) return onePredicate;
            switch (composedType) {
                case AND:
                    return criteriaBuilder.and(onePredicate, otherPredicate);
                case OR:
                    return criteriaBuilder.or(onePredicate, otherPredicate);
            }
            throw new RuntimeException();
        };
    }

    enum ComposedType {
        AND, OR
    }

    @Nullable
    Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);
}
