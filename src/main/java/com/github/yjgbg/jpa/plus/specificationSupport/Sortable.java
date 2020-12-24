package com.github.yjgbg.jpa.plus.specificationSupport;

import com.github.yjgbg.jpa.plus.utils.Getter;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Sort.Direction;

/**
 * 排序功能，模板模式
 * @param <Self>
 */
interface Sortable<Self> {
  Self sort(Sort sort);

  default Self sort(Sort.Order... orders) {
    return sort(Sort.by(orders));
  }

  default Self sort(List<Sort.Order> orders) {
    return sort(Sort.by(orders));
  }

  default Self sortAsc(String... properties) {
    return sort(Sort.by(Direction.ASC,properties));
  }

  default Self sortDesc(String... properties) {
    return sort(Sort.by(Direction.DESC,properties));
  }

  default Self sortAsc(Getter<?,?>... properties) {
    return sort(Sort.by(Direction.ASC,Arrays.stream(properties).map(Getter::propertyName).toArray(String[]::new)));
  }

  default Self sortDesc(Getter<?,?>... properties) {
    return sort(Sort.by(Direction.DESC,Arrays.stream(properties).map(Getter::propertyName).toArray(String[]::new)));
  }
}
