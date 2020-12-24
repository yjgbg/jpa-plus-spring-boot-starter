package com.github.yjgbg.jpa.plus.specificationSupport;

import com.github.yjgbg.jpa.plus.repository.JpaSpecificationRepository;
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
   * @return
   */
  default ExecutableSpecification<T> spec() {
    return new ExecutableSpecification<>(this);
  }
}
