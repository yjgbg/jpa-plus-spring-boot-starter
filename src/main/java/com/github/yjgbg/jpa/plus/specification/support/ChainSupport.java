package com.github.yjgbg.jpa.plus.specification.support;

import com.github.yjgbg.jpa.plus.config.Constants;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 该类用于组织查询条件，参数path为属性的名称，参数value为属性的值
 * 查询Specification对象的组装
 *
 * @param <T>
 * @param <Self>
 */
public interface ChainSupport<T, Self extends ChainSupport<T, Self>> {
  @NotNull
  Self and(@Nullable Specification<T> specification);

  @NotNull
  default Self example(@NotNull T probe) {
    return and((root, query, criteriaBuilder) ->
            QueryByExamplePredicateBuilder.getPredicate(root, criteriaBuilder, Example.of(probe)));
  }

  @NotNull
  @SuppressWarnings("unchecked")
  default Self cond(boolean condition, @NotNull Function<Self, @NotNull Self> functor) {
    return condition?functor.apply((Self) this):(Self) this;
  }

  @NotNull
  default <P extends Comparable<P>> Self le(@NotNull String path, @Nullable P value) {
    return cond(value != null, x -> x.and((root, query, criteriaBuilder) ->
            criteriaBuilder.lessThanOrEqualTo(str2Path(root, path), value)));
  }

  @NotNull
  default <P extends Comparable<P>> Self ge(@NotNull String path, @Nullable P value) {
    return cond(value != null, x -> x.and((root, query, criteriaBuilder) ->
            criteriaBuilder.greaterThanOrEqualTo(str2Path(root, path), value)));
  }

  @NotNull
  default <P extends Comparable<P>> Self lt(@NotNull String path, @Nullable P value) {
    return cond(value != null, x -> x.and((root, query, criteriaBuilder) ->
        criteriaBuilder.lessThan(str2Path(root, path), value)));
  }

  @NotNull
  default <P extends Comparable<P>> Self gt(@NotNull String path, @Nullable P value) {
    return cond(value != null, x -> x.and((root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThan(str2Path(root, path), value)));
  }

  // 闭区间
  @NotNull
  default <P extends Comparable<P>> Self between(
      @NotNull String path, @Nullable P lowerBound, @Nullable P upperBound) {
    return ge(path, lowerBound).le(path, upperBound);
  }

  @NotNull
  default Self like(@NotNull String path, String value) {
    return and((root, query, criteriaBuilder) ->
        criteriaBuilder.like(str2Path(root, path), value, Constants.DEFAULT_ESCAPE_CHAR));
  }

  @NotNull
  default Self like(@NotNull String path,String value,char escape) {
    return and((root, query, cb) -> cb.like(str2Path(root,path),value,escape));
  }

  @NotNull
  default Self in(@NotNull String path, @NotNull Collection<?> value) {
    return and((root, query, cb) -> value.size() == 0 ? cb.or()// 永假 cb.or
        : value.size() == 1 && value.iterator().next()==null ? cb.isNull(str2Path(root,path))
        : value.size() == 1 ? cb.equal(str2Path(root,path),value.iterator().next())
        : also(cb.in(str2Path(root,path)),in -> value.forEach(in::value)));
  }

  @NotNull
  default Self in(@NotNull String path, Object... value) {
    return and((root, query, cb) -> value.length == 0 ? cb.or()// 永假 cb.or
        : value.length == 1 && value[0]==null ? cb.isNull(str2Path(root,path))
        : value.length == 1 ? cb.equal(str2Path(root,path),value[0])
        : also(cb.in(str2Path(root,path)),in -> Arrays.stream(value).forEach(in::value)));
  }

  @NotNull
  default Self notIn(@NotNull String path, @NotNull Collection<?> value) {
    return and((root, query, cb) -> value.size() == 0 ? cb.and()// 永假 cb.or
        : value.size() == 1 && value.iterator().next()==null ? cb.isNotNull(str2Path(root,path))
        : value.size() == 1 ? cb.notEqual(str2Path(root,path),value.iterator().next())
        : cb.in(str2Path(root,path)).in(value).not());
  }

  @NotNull
  default Self notIn(@NotNull String path, Object... value) {
    return and((root, query, cb) -> value.length == 0 ? cb.and()// 永假 cb.or
        : value.length == 1 && value[0]==null ? cb.isNotNull(str2Path(root,path))
        : value.length == 1 ? cb.notEqual(str2Path(root,path),value[0])
        : cb.in(str2Path(root,path)).in(value).not());
  }

  @NotNull
  @Contract(pure = true)
  static <P> Path<P> str2Path(@NotNull Path<?> root, @NotNull String string) {
    @SuppressWarnings("unchecked")
    final var path = (Path<P>) root;
    return Arrays.stream(string.split("\\."))
        .reduce(path, Path::get, (a, b) -> null);
  }
  private static <A> A also(A a, Consumer<A> consumer) {
    consumer.accept(a);
    return a;
  }
}
