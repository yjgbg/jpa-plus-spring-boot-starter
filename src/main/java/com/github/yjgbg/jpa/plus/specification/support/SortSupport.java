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
public interface SortSupport<Self> {
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

  default Self sort(Direction direction, Getter<?, ?>... properties) {
    final var propsStrings = Arrays.stream(properties).map(Getter::propertyName).toArray(String[]::new);
    return sort(Sort.by(direction, propsStrings));
  }

  default Self sortAsc(Getter<?, ?>... properties) {
    final var propsStrings = Arrays.stream(properties).map(Getter::propertyName).toArray(String[]::new);
    return sort(Sort.by(Direction.ASC, propsStrings));
  }

  default Self sortDesc(Getter<?, ?>... properties) {
    final var propsStrings = Arrays.stream(properties).map(Getter::propertyName).toArray(String[]::new);
    return sort(Sort.by(Direction.DESC, propsStrings));
  }
}
