package com.github.yjgbg.jpa.plus.specificationSupport;

import com.github.yjgbg.jpa.plus.utils.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface GetterSupport<T, Self extends GetterSupport<T, Self>>
        extends ChainSpecification<T, Self> {

    default <R> Self eq(Getter<T, R> path, R value) {
        return eq(path.propertyName(),value);
    }

    default <R> Self eq(boolean condition, Getter<T, R> path, R value) {
        return eq(condition,path.propertyName(),value);
    }

    default <R> @NotNull Self ne(@NotNull Getter<T, R> path, @Nullable R value) {
        return ne(path.propertyName(),value);
    }

    default <R> @NotNull Self ne(boolean condition, @NotNull Getter<T, R> path, @Nullable R value) {
        return ne(condition,path.propertyName(),value);
    }

    default <R extends Comparable<R>> @NotNull Self le(@NotNull Getter<T, R> path, @Nullable R value) {
        return le(path.propertyName(),value);
    }

    default <R extends Comparable<R>> @NotNull Self ge(@NotNull Getter<T, R> path, @Nullable R value) {
        return ge(path.propertyName(),value);
    }

    default <R extends Comparable<R>> @NotNull Self lt(@NotNull Getter<T, R> path, @Nullable R value) {
        return lt(path.propertyName(),value);
    }

    default <R extends Comparable<R>> @NotNull Self gt(@NotNull Getter<T, R> path, @Nullable R value) {
        return gt(path.propertyName(),value);
    }

    default <R extends Comparable<R>> @NotNull Self between(@NotNull Getter<T, R> path, @Nullable R lowerBound, @Nullable R upperBound) {
        return between(path.propertyName(),lowerBound,upperBound);
    }

    default <R> @NotNull Self like(@NotNull Getter<T, String> path, String value) {
        return like(path.propertyName(),value);
    }

    default <R> @NotNull Self like(boolean condition, @NotNull Getter<T, String> path, String value) {
        return like(condition,path.propertyName(),value);
    }

    default <R> @NotNull Self in(@NotNull Getter<T, R> path, @NotNull Collection<R> value) {
        return in(path.propertyName(), value);
    }

    @SuppressWarnings("unchecked")
    default <R> @NotNull Self in(@NotNull Getter<T, R> path, R... value) {
        return in(path.propertyName(),(Object[]) value);
    }

    default <R> @NotNull Self notIn(@NotNull Getter<T, R> path, @NotNull Collection<R> value) {
        return notIn(path.propertyName(),value);
    }

    @SuppressWarnings("unchecked")
    default <R> @NotNull Self notIn(@NotNull Getter<T, R> path, R... value) {
        return notIn(path.propertyName(),(Object[]) value);
    }


}
