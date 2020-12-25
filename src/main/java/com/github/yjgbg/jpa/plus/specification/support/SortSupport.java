package com.github.yjgbg.jpa.plus.specification.support;

import com.github.yjgbg.jpa.plus.utils.Getter;
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

  default Self sortAsc(String... properties) {
    return sort(Sort.by(Direction.ASC,properties));
  }

  default Self sortDesc(String... properties) {
    return sort(Sort.by(Direction.DESC,properties));
  }

  private Self asc(Getter<?, ?>... properties) {
    return sort(Sort.by(Direction.ASC, Arrays.stream(properties).map(Getter::propertyName).toArray(String[]::new)));
  }

  default Self desc(Getter<?, ?>... properties) {
    return sort(Sort.by(Direction.DESC, Arrays.stream(properties).map(Getter::propertyName).toArray(String[]::new)));
  }
}
