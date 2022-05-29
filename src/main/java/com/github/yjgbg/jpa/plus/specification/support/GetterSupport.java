package com.github.yjgbg.jpa.plus.specification.support;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface GetterSupport<T, Self extends GetterSupport<T, Self>>
        extends ChainSupport<T, Self> {
    default <P extends Comparable<P>> @NotNull Self le(@NotNull StaticMethodReferenceGetter<T, P> path, @Nullable P value) {
        return le(path.propertyName(),value);
    }

    default <P extends Comparable<P>> @NotNull Self ge(@NotNull StaticMethodReferenceGetter<T, P> path, @Nullable P value) {
        return ge(path.propertyName(),value);
    }

    default <P extends Comparable<P>> @NotNull Self lt(@NotNull StaticMethodReferenceGetter<T, P> path, @Nullable P value) {
        return lt(path.propertyName(),value);
    }

    default <P extends Comparable<P>> @NotNull Self gt(@NotNull StaticMethodReferenceGetter<T, P> path, @Nullable P value) {
        return gt(path.propertyName(),value);
    }

    default <P extends Comparable<P>> @NotNull Self between(@NotNull StaticMethodReferenceGetter<T, P> path, @Nullable P lowerBound, @Nullable P upperBound) {
        return between(path.propertyName(),lowerBound,upperBound);
    }

    default @NotNull Self like(@NotNull StaticMethodReferenceGetter<T, String> path, String value) {
        return like(path.propertyName(),value);
    }

    default @NotNull Self like(@NotNull StaticMethodReferenceGetter<T, String> path, String value, char escape) {
        return like(path.propertyName(),value,escape);
    }

    default <P> @NotNull Self in(@NotNull StaticMethodReferenceGetter<T, P> path, @NotNull Collection<P> value) {
        return in(path.propertyName(), value);
    }

    @SuppressWarnings("unchecked")
    default <P> @NotNull Self in(@NotNull StaticMethodReferenceGetter<T, P> path, P... value) {
        return in(path.propertyName(),(Object[]) value);
    }

    default <P> @NotNull Self notIn(@NotNull StaticMethodReferenceGetter<T, P> path, @NotNull Collection<P> value) {
        return notIn(path.propertyName(),value);
    }

    @SuppressWarnings("unchecked")
    default <P> @NotNull Self notIn(@NotNull StaticMethodReferenceGetter<T, P> path, P... value) {
        return notIn(path.propertyName(),(Object[]) value);
    }


}
