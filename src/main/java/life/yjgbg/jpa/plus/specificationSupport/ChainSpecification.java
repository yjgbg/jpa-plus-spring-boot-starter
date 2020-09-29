package life.yjgbg.jpa.plus.specificationSupport;

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
 * 查询Specification对象的组装
 * @param <T>
 * @param <Self>
 */

public interface ChainSpecification<T,Self extends ChainSpecification<T,Self>> {
  @NotNull
  Self and(@Nullable Specification<T> specification);

  default Self eq(String path,Object value) {
    if (value == null) return and((root, query, criteriaBuilder) ->
        criteriaBuilder.isNull(str2Path(root, path)));
    return and((root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get(path), value));
  }

  default Self eq(boolean condition,String path,Object value) {
    return cond(condition,x -> x.eq(path, value));
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
    if (value == null) return and((root, query, criteriaBuilder) ->
        criteriaBuilder.isNotNull(root.get(path)));
    return and((root, query, criteriaBuilder) ->
        criteriaBuilder.notEqual(root.get(path), value));
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
  default Self in(@NotNull String path, @NotNull Collection<Object> value) {
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
  private <P> Path<P> str2Path(@NotNull Path<?> path, @NotNull String string) {
    @SuppressWarnings("unchecked")
    val castPath = (Path<P>) path;
    return Arrays.stream(string.split("\\."))
        .reduce(castPath, Path::get, (a, b) -> a);
  }
}
