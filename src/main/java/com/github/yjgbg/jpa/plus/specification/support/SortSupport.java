package com.github.yjgbg.jpa.plus.specification.support;

import lombok.val;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 排序功能，模板模式
 *
 * @param <Self>
 */
public interface SortSupport<Self, A> {
  Self sort(Sort sort);

  default Self sort(Sort.Order... orders) {
    return sort(Sort.by(orders));
  }

  default Self sort(List<Sort.Order> orders) {
    return sort(Sort.by(orders));
  }

  default Self sort(Direction direction, String... properties) {
    return sort(Sort.by(direction, properties));
  }

  default Self sortAsc(String... properties) {
    return sort(Sort.by(Direction.ASC, properties));
  }

  default Self sortDesc(String... properties) {
    return sort(Sort.by(Direction.DESC, properties));
  }

  default Self sort(Direction direction, List<StaticMethodReferenceGetter<A, ?>> getters) {
    val propsStrings = getters.stream().map(StaticMethodReferenceGetter::propertyName).toArray(String[]::new);
    return sort(Sort.by(direction, propsStrings));
  }

  default Self sortAsc(List<StaticMethodReferenceGetter<A, ?>> properties) {
    return sort(Direction.ASC, properties);
  }

  default Self sortDesc(List<StaticMethodReferenceGetter<A, ?>> properties) {
    return sort(Direction.DESC, properties);
  }

  default Self sortAsc(StaticMethodReferenceGetter<A, ?> g0) {
    return sortAsc(Collections.singletonList(g0));
  }

  default Self sortAsc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1) {
    return sortAsc(Arrays.asList(g0, g1));
  }

  default Self sortAsc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1,
                       StaticMethodReferenceGetter<A, ?> g2) {
    return sortAsc(Arrays.asList(g0, g1, g2));
  }

  default Self sortAsc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1,
                       StaticMethodReferenceGetter<A, ?> g2, StaticMethodReferenceGetter<A, ?> g3) {
    return sortAsc(Arrays.asList(g0, g1, g2, g3));
  }

  default Self sortAsc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1,
                       StaticMethodReferenceGetter<A, ?> g2, StaticMethodReferenceGetter<A, ?> g3,
                       StaticMethodReferenceGetter<A, ?> g4) {
    return sortAsc(Arrays.asList(g0, g1, g2, g3, g4));
  }

  default Self sortAsc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1,
                       StaticMethodReferenceGetter<A, ?> g2, StaticMethodReferenceGetter<A, ?> g3,
                       StaticMethodReferenceGetter<A, ?> g4, StaticMethodReferenceGetter<A, ?> g5) {
    return sortAsc(Arrays.asList(g0, g1, g2, g3, g4, g5));
  }

  default Self sortAsc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1,
                       StaticMethodReferenceGetter<A, ?> g2, StaticMethodReferenceGetter<A, ?> g3,
                       StaticMethodReferenceGetter<A, ?> g4, StaticMethodReferenceGetter<A, ?> g5,
                       StaticMethodReferenceGetter<A, ?> g6) {
    return sortAsc(Arrays.asList(g0, g1, g2, g3, g4, g5, g6));
  }

  default Self sortAsc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1,
                       StaticMethodReferenceGetter<A, ?> g2, StaticMethodReferenceGetter<A, ?> g3,
                       StaticMethodReferenceGetter<A, ?> g4, StaticMethodReferenceGetter<A, ?> g5,
                       StaticMethodReferenceGetter<A, ?> g6, StaticMethodReferenceGetter<A, ?> g7) {
    return sortAsc(Arrays.asList(g0, g1, g2, g3, g4, g5, g6, g7));
  }

  default Self sortAsc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1,
                       StaticMethodReferenceGetter<A, ?> g2, StaticMethodReferenceGetter<A, ?> g3,
                       StaticMethodReferenceGetter<A, ?> g4, StaticMethodReferenceGetter<A, ?> g5,
                       StaticMethodReferenceGetter<A, ?> g6, StaticMethodReferenceGetter<A, ?> g7,
                       StaticMethodReferenceGetter<A, ?> g8) {
    return sortAsc(Arrays.asList(g0, g1, g2, g3, g4, g5, g6, g7, g8));
  }

  default Self sortAsc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1,
                       StaticMethodReferenceGetter<A, ?> g2, StaticMethodReferenceGetter<A, ?> g3,
                       StaticMethodReferenceGetter<A, ?> g4, StaticMethodReferenceGetter<A, ?> g5,
                       StaticMethodReferenceGetter<A, ?> g6, StaticMethodReferenceGetter<A, ?> g7,
                       StaticMethodReferenceGetter<A, ?> g8, StaticMethodReferenceGetter<A, ?> g9) {
    return sortAsc(Arrays.asList(g0, g1, g2, g3, g4, g5, g6, g7, g8, g9));
  }

  default Self sortDesc(StaticMethodReferenceGetter<A, ?> g0) {
    return sortDesc(Collections.singletonList(g0));
  }

  default Self sortDesc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1) {
    return sortDesc(Arrays.asList(g0, g1));
  }

  default Self sortDesc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1,
                        StaticMethodReferenceGetter<A, ?> g2) {
    return sortDesc(Arrays.asList(g0, g1, g2));
  }

  default Self sortDesc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1,
                        StaticMethodReferenceGetter<A, ?> g2, StaticMethodReferenceGetter<A, ?> g3) {
    return sortDesc(Arrays.asList(g0, g1, g2, g3));
  }

  default Self sortDesc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1,
                        StaticMethodReferenceGetter<A, ?> g2, StaticMethodReferenceGetter<A, ?> g3,
                        StaticMethodReferenceGetter<A, ?> g4) {
    return sortDesc(Arrays.asList(g0, g1, g2, g3, g4));
  }

  default Self sortDesc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1,
                        StaticMethodReferenceGetter<A, ?> g2, StaticMethodReferenceGetter<A, ?> g3,
                        StaticMethodReferenceGetter<A, ?> g4, StaticMethodReferenceGetter<A, ?> g5) {
    return sortDesc(Arrays.asList(g0, g1, g2, g3, g4,g5));
  }

  default Self sortDesc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1,
                        StaticMethodReferenceGetter<A, ?> g2, StaticMethodReferenceGetter<A, ?> g3,
                        StaticMethodReferenceGetter<A, ?> g4, StaticMethodReferenceGetter<A, ?> g5,
                        StaticMethodReferenceGetter<A, ?> g6) {
    return sortDesc(Arrays.asList(g0, g1, g2, g3, g4,g5,g6));
  }

  default Self sortDesc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1,
                        StaticMethodReferenceGetter<A, ?> g2, StaticMethodReferenceGetter<A, ?> g3,
                        StaticMethodReferenceGetter<A, ?> g4, StaticMethodReferenceGetter<A, ?> g5,
                        StaticMethodReferenceGetter<A, ?> g6, StaticMethodReferenceGetter<A, ?> g7) {
    return sortDesc(Arrays.asList(g0, g1, g2, g3, g4,g5,g6,g7));
  }

  default Self sortDesc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1,
                        StaticMethodReferenceGetter<A, ?> g2, StaticMethodReferenceGetter<A, ?> g3,
                        StaticMethodReferenceGetter<A, ?> g4, StaticMethodReferenceGetter<A, ?> g5,
                        StaticMethodReferenceGetter<A, ?> g6, StaticMethodReferenceGetter<A, ?> g7,
                        StaticMethodReferenceGetter<A, ?> g8) {
    return sortDesc(Arrays.asList(g0, g1, g2, g3, g4,g5,g6,g7,g8));
  }

  default Self sortDesc(StaticMethodReferenceGetter<A, ?> g0, StaticMethodReferenceGetter<A, ?> g1,
                        StaticMethodReferenceGetter<A, ?> g2, StaticMethodReferenceGetter<A, ?> g3,
                        StaticMethodReferenceGetter<A, ?> g4, StaticMethodReferenceGetter<A, ?> g5,
                        StaticMethodReferenceGetter<A, ?> g6, StaticMethodReferenceGetter<A, ?> g7,
                        StaticMethodReferenceGetter<A, ?> g8, StaticMethodReferenceGetter<A, ?> g9) {
    return sortDesc(Arrays.asList(g0, g1, g2, g3, g4,g5,g6,g7,g8,g9));
  }
}
