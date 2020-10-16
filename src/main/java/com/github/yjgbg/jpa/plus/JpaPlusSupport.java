package com.github.yjgbg.jpa.plus;

import com.github.yjgbg.jpa.plus.aop.ReturnPropsSetNullProcessing;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Getter
@Configuration
@EnableJpaAuditing
public class JpaPlusSupport {

  @Autowired
  private EntityManager entityManager;

  public static JpaPlusSupport SELF;

  @Autowired
  public void injectSelf(JpaPlusSupport self) {
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
