package com.github.yjgbg.jpa.plus.specificationSupport;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface StdRepository<T>
    extends JpaSpecificationExecutor<T>, Repository<T, Long> {
  /**
   * 获取到一个可执行(find，count等函数)的Specification
   * @return
   */
  default ExecutableSpecification<T> spec() {
    return new ExecutableSpecification<>(this);
  }
}
