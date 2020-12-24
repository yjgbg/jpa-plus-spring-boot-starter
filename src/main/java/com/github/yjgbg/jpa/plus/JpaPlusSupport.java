package com.github.yjgbg.jpa.plus;

import com.github.yjgbg.jpa.plus.aop.ReturnPropsSetNullProcessing;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.github.yjgbg.jpa.plus.entitySupport.ActiveEntityHelper;
import com.github.yjgbg.jpa.plus.repository.JpaSpecificationRepositoryImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Yjgbg
 */
@Getter
@Lazy(false)
@Configuration
@RequiredArgsConstructor
public class JpaPlusSupport {
  private final EntityManager entityManager;
  public static JpaPlusSupport SELF;

  @Autowired
  public void injectSelf(JpaPlusSupport self) {
    ActiveEntityHelper.registerRemoveFunction(self::remove);
    ActiveEntityHelper.registerSaveFunction(self::save);
    SELF = self;
  }


  @Modifying
  @Transactional
  public <T> T save(T entity) {
    return entityManager.merge(entity);
  }

  @Modifying
  @Transactional
  public void remove(Object entity) {
    entityManager.remove(entity);
  }

  @Bean
  public ReturnPropsSetNullProcessing returnPropsSetNullProcessing() {
    return new ReturnPropsSetNullProcessing();
  }
}
