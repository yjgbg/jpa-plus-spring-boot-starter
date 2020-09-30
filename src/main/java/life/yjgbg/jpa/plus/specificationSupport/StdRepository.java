package life.yjgbg.jpa.plus.specificationSupport;

import life.yjgbg.jpa.plus.entitySupport.StdEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface StdRepository<T extends StdEntity<T>>
    extends JpaSpecificationExecutor<T>, Repository<T, Long> {

  // 包含已经逻辑删除的字段
  default ExecutableSpecification<T> specIncludeDeletedLogically() {
    return new ExecutableSpecification<>(this);
  }

  // 不包含已经逻辑删除的字段
  default ExecutableSpecification<T> spec() {
    return specIncludeDeletedLogically().eq("deleted",false);
  }
}
