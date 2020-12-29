package com.github.yjgbg.jpa.plus.specification.support;

import lombok.val;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import java.util.Arrays;
import java.util.Collection;
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

  /**
   * 查询条件：prop所代表的属性是否等于value
   *
   * @param path
   * @param value
   * @return
   */
  default Self eq(String path, Object value) {
    if (value == null) {
      return and((root, query, criteriaBuilder) ->
              criteriaBuilder.isNull(str2Path(root, path)));
    }
    return and((root, query, criteriaBuilder) ->
            criteriaBuilder.equal(str2Path(root, path), value));
  }

  /**
   * 根据condition的真值决定是否需要此条查询条件
   * 作用相当于：
   * if(condition) spec = spec.eq(path,value);
   *
   * @param condition
   * @param path
   * @param value
   * @return
   */
  default Self eq(boolean condition, String path, Object value) {
    return cond(condition, x -> x.eq(path, value));
  }

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
  default Self ne(@NotNull String path, @Nullable Object value) {
    if (value == null) {
      return and((root, query, criteriaBuilder) ->
              criteriaBuilder.isNotNull(str2Path(root, path)));
    }
    return and((root, query, criteriaBuilder) ->
            criteriaBuilder.notEqual(str2Path(root, path), value));
  }

  @NotNull
  default Self ne(boolean condition,@NotNull String path, @Nullable Object value) {
    return cond(condition,x -> x.ne(path, value));
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
        criteriaBuilder.like(str2Path(root, path), value));
  }

  @NotNull
  default Self like(boolean condition, @NotNull String path, String value) {
    return cond(condition, spec -> spec.like(path, value));
  }

  @NotNull
  default <R> Self in(@NotNull String path, @NotNull Collection<R> value) {
    return and((root, query, criteriaBuilder) -> {
      val p = criteriaBuilder.in(str2Path(root, path));
      value.forEach(p::value);
      return p;
    });
  }

  @NotNull
  default Self in(@NotNull String path, Object... value) {
    return and((root, query, criteriaBuilder) -> {
      val p = criteriaBuilder.in(str2Path(root, path));
      Arrays.stream(value).forEach(p::value);
      return p;
    });
  }

  @NotNull
  default Self notIn(@NotNull String path, @NotNull Collection<Object> value) {
    return and(Specification.not((root, query, criteriaBuilder) -> {
      val p = criteriaBuilder.in(str2Path(root, path));
      value.forEach(p::value);
      return p;
    }));
  }

  @NotNull
  default Self notIn(@NotNull String path, Object... value) {
    return and(Specification.not((root, query, criteriaBuilder) -> {
      val p = criteriaBuilder.in(str2Path(root, path));
      Arrays.stream(value).forEach(p::value);
      return p;
    }));
  }

  @NotNull
  @Contract(pure = true)
  static <P> Path<P> str2Path(@NotNull Path<?> root, @NotNull String string) {
    @SuppressWarnings("unchecked")
    val path = (Path<P>) root;
    return Arrays.stream(string.split("\\."))
        .reduce(path, Path::get, (a, b) -> a);
  }
}
