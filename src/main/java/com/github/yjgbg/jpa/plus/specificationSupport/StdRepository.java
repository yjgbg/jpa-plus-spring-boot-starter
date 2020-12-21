package com.github.yjgbg.jpa.plus.specificationSupport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface StdRepository<T>
    extends JpaSpecificationExecutor<T>, JpaRepository<T, Long> {
  /**
   * 获取到一个可执行(find，count等函数)的Specification
   * @return
   */
  default ExecutableSpecification<T> spec() {
    return new ExecutableSpecification<>(this);
  }
}
