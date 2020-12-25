package com.github.yjgbg.jpa.plus.repository;

import com.github.yjgbg.jpa.plus.specification.ExecutableSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author yjgbg
 */
@NoRepositoryBean
public interface StdRepository<T,ID>
    extends JpaSpecificationRepository<T>, JpaRepository<T,ID> {
  /**
   * 获取到一个可执行(find，count等函数)的Specification
   *
   * @return 可执行(find ， count等函数)的Specification
   */
  default ExecutableSpecification<T> spec() {
    return new ExecutableSpecification<>(this);
  }
}
