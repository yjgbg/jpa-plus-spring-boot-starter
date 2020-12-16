package com.github.yjgbg.jpa.plus.specificationSupport;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface StdRepository<T>
    extends JpaSpecificationExecutor<T>, Repository<T, Long> {
  // 包含已经逻辑删除的字段
  default ExecutableSpecification<T> spec() {
    return new ExecutableSpecification<>(this);
  }
}
