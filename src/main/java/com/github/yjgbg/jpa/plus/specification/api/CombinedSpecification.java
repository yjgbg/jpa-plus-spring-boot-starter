package com.github.yjgbg.jpa.plus.specification.api;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

@RequiredArgsConstructor
public class CombinedSpecification<T> implements Specification<T> {
    private final List<Specification<T>> contents = new ArrayList<>();
    private final Predicate.BooleanOperator combinedType;

    private BinaryOperator<Specification<T>> combinedFunction() {
        return combinedType == Predicate.BooleanOperator.AND ? Specifications::and : Specifications::or;
    }

    protected void add(Specification<T> specification) {
        contents.add(specification);
    }

    @Nullable
    public Specification<T> getLast() {
        if (contents.size() == 0) return null;
        return contents.get(contents.size() - 1);
    }

    public static <T> CombinedSpecification<T> and() {
        return new CombinedSpecification<>(Predicate.BooleanOperator.AND);
    }

    public static <T> CombinedSpecification<T> or() {
        return new CombinedSpecification<>(Predicate.BooleanOperator.OR);
    }

    @Override
    public @Nullable Predicate predicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return contents.stream().reduce(combinedFunction())
                .map(x -> x.predicate(root, query, criteriaBuilder))
                .orElse(null);
    }
}
