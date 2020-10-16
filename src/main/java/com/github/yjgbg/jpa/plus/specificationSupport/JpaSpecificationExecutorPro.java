package com.github.yjgbg.jpa.plus.specificationSupport;

import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.lang.Nullable;

public interface JpaSpecificationExecutorPro<T> extends JpaSpecificationExecutor<T> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<T> findOneForUpdate(@Nullable Specification<T> spec);

}
