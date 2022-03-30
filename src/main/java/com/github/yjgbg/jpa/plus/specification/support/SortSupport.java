package com.github.yjgbg.jpa.plus.specification.support;

import lombok.var;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.Arrays;
import java.util.List;

/**
 * 排序功能，模板模式
 *
 * @param <Self>
 */
public interface SortSupport<Self,A> {
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

  default Self sort(Direction direction, Getter<A, ?>... properties) {
    final var propsStrings = Arrays.stream(properties).map(Getter::propertyName).toArray(String[]::new);
    return sort(Sort.by(direction, propsStrings));
  }

  default Self sortAsc(Getter<A, ?> property0) {
    return sort(Direction.ASC,property0);
  }

  default Self sortAsc(Getter<A, ?> property0,Getter<A, ?> property1) {
    return sort(Direction.ASC,property0,property1);
  }

  default Self sortAsc(Getter<A, ?> property0,Getter<A, ?> property1,Getter<A, ?> property2) {
    return sort(Direction.ASC,property0,property1,property2);
  }

  default Self sortAsc(Getter<A, ?> property0,Getter<A, ?> property1,Getter<A, ?> property2,Getter<A, ?> property3) {
    return sort(Direction.ASC,property0,property1,property2,property3);
  }

  default Self sortAsc(Getter<A, ?> property0,Getter<A, ?> property1,Getter<A, ?> property2,Getter<A, ?> property3,Getter<A, ?> property4) {
    return sort(Direction.ASC,property0,property1,property2,property3,property4);
  }
  default Self sortAsc(Getter<A, ?>... properties) {
    return sort(Direction.ASC, properties);
  }
  default Self sortDesc(Getter<A, ?> property0) {
    return sort(Direction.DESC,property0);
  }

  default Self sortDesc(Getter<A, ?> property0,Getter<A, ?> property1) {
    return sort(Direction.DESC,property0,property1);
  }

  default Self sortDesc(Getter<A, ?> property0,Getter<A, ?> property1,Getter<A, ?> property2) {
    return sort(Direction.DESC,property0,property1,property2);
  }

  default Self sortDesc(Getter<A, ?> property0,Getter<A, ?> property1,Getter<A, ?> property2,Getter<A, ?> property3) {
    return sort(Direction.DESC,property0,property1,property2,property3);
  }

  default Self sortDesc(Getter<A, ?> property0,Getter<A, ?> property1,Getter<A, ?> property2,Getter<A, ?> property3,Getter<A, ?> property4) {
    return sort(Direction.DESC,property0,property1,property2,property3,property4);
  }
  default Self sortDesc(Getter<A, ?>... properties) {
    return sort(Direction.DESC, properties);
  }
}
