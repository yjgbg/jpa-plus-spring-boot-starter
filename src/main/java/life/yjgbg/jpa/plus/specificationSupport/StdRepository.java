package life.yjgbg.jpa.plus.specificationSupport;

import life.yjgbg.jpa.plus.entitySupport.StdEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface StdRepository<T extends StdEntity<T>>
    extends JpaSpecificationExecutor<T>, Repository<T, Long> {

  // 是否包含逻辑删除的字段:true 包含，false不包含，默认false
  default ExecutableSpecification<T> specIncludeDeletedLogically() {
    return new ExecutableSpecification<>(this);
  }

  // 是否包含逻辑删除的字段:true 包含，false不包含，默认false
  default ExecutableSpecification<T> spec() {
    return specIncludeDeletedLogically().eq("deleted",false);
  }
}
