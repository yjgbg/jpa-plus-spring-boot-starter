package com.github.yjgbg.jpa.plus.specification.api;

import lombok.experimental.ExtensionMethod;
import lombok.val;
import org.jetbrains.annotations.Nullable;

import static com.github.yjgbg.jpa.plus.utils.PathUtils.str2Path;

@ExtensionMethod(Specification.class)
public class Specifications {
    public static <T> Specification<T> not(Specification<T> spec) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.not(spec.predicate(root, query, criteriaBuilder));
    }

    public static <T> Specification<T> and(@Nullable Specification<T> that, @Nullable Specification<T> other) {
        return (root, query, criteriaBuilder) -> {
            val onePredicate = that == null ? null : that.predicate(root, query, criteriaBuilder);
            val otherPredicate = other == null ? null : other.predicate(root, query, criteriaBuilder);
            return onePredicate == null ? otherPredicate
                    : otherPredicate == null ? onePredicate
                    : criteriaBuilder.and(onePredicate, otherPredicate);
        };
    }

    public static <T> Specification<T> or(Specification<T> that, Specification<T> other) {
        return (root, query, criteriaBuilder) -> {
            val onePredicate = that == null ? null : that.predicate(root, query, criteriaBuilder);
            val otherPredicate = other == null ? null : other.predicate(root, query, criteriaBuilder);
            return onePredicate == null ? otherPredicate
                    : otherPredicate == null ? onePredicate
                    : criteriaBuilder.or(onePredicate, otherPredicate);
        };
    }

    public static <T> Specification<T> eq(Specification<T> that, String path, Object value) {
        return value == null
                ? and(that, (root, query, cb) -> cb.isNull(str2Path(root, path)))
                : and(that, (root, query, cb) -> cb.equal(str2Path(root, path), value));
    }
}
