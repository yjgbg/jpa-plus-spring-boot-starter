package com.github.yjgbg.jpa.plus.specification.support;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface GetterSupport<T, Self extends GetterSupport<T, Self>>
        extends ChainSupport<T, Self> {

    @Deprecated
    default <P> Self eq(Getter<T, P> path, P value) {
        return eq(path.propertyName(), value);
    }

    @Deprecated
    default <P> Self eq(boolean condition, Getter<T, P> path, P value) {
        return eq(condition, path.propertyName(), value);
    }

    default <P> @NotNull Self ne(@NotNull Getter<T, P> path, @Nullable P value) {
        return ne(path.propertyName(),value);
    }

    default <P> @NotNull Self ne(boolean condition, @NotNull Getter<T, P> path, @Nullable P value) {
        return ne(condition,path.propertyName(),value);
    }

    default <P extends Comparable<P>> @NotNull Self le(@NotNull Getter<T, P> path, @Nullable P value) {
        return le(path.propertyName(),value);
    }

    default <P extends Comparable<P>> @NotNull Self ge(@NotNull Getter<T, P> path, @Nullable P value) {
        return ge(path.propertyName(),value);
    }

    default <P extends Comparable<P>> @NotNull Self lt(@NotNull Getter<T, P> path, @Nullable P value) {
        return lt(path.propertyName(),value);
    }

    default <P extends Comparable<P>> @NotNull Self gt(@NotNull Getter<T, P> path, @Nullable P value) {
        return gt(path.propertyName(),value);
    }

    default <P extends Comparable<P>> @NotNull Self between(@NotNull Getter<T, P> path, @Nullable P lowerBound, @Nullable P upperBound) {
        return between(path.propertyName(),lowerBound,upperBound);
    }

    default <P> @NotNull Self like(@NotNull Getter<T, String> path, String value) {
        return like(path.propertyName(),value);
    }

    default <P> @NotNull Self like(boolean condition, @NotNull Getter<T, String> path, String value) {
        return like(condition,path.propertyName(),value);
    }

    default <P> @NotNull Self in(@NotNull Getter<T, P> path, @NotNull Collection<P> value) {
        return in(path.propertyName(), value);
    }

    @SuppressWarnings("unchecked")
    default <P> @NotNull Self in(@NotNull Getter<T, P> path, P... value) {
        return in(path.propertyName(),(Object[]) value);
    }

    default <P> @NotNull Self notIn(@NotNull Getter<T, P> path, @NotNull Collection<P> value) {
        return notIn(path.propertyName(),value);
    }

    @SuppressWarnings("unchecked")
    default <P> @NotNull Self notIn(@NotNull Getter<T, P> path, P... value) {
        return notIn(path.propertyName(),(Object[]) value);
    }


}
