package life.yjgbg.jpa.plus.specificationSupport;

import org.springframework.data.domain.Sort;

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

  default Self sort(Sort.Direction direction, String... properties) {
    return sort(Sort.by(direction,properties));
  }

  default Self sort(List<Sort.Order> orders) {
    return sort(Sort.by(orders));
  }

  default Self sortAsc(String... properties) {
    return sort(Direction.ASC,properties);
  }

  default Self sortDesc(String... properties) {
    return sort(Direction.DESC,properties);
  }
}
