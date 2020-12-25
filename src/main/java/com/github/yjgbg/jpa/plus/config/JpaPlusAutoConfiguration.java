package com.github.yjgbg.jpa.plus.config;

import com.github.yjgbg.jpa.plus.annotations.processing.ReturnPropsSetNullProcessing;
import com.github.yjgbg.jpa.plus.entitySupport.ActiveEntityHelper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Getter
@RequiredArgsConstructor
@SpringBootConfiguration
@ConditionalOnClass(JpaPlusAutoConfiguration.class)
public class JpaPlusAutoConfiguration {
    public static JpaPlusAutoConfiguration SELF;
    public final EntityManager entityManager;

    @Autowired
    public void injectSelf(JpaPlusAutoConfiguration self) {
        ActiveEntityHelper.registerRemoveFunction(self::remove); // 注册ActiveEntity
        ActiveEntityHelper.registerSaveFunction(self::save); // 注册ActiveEntity
        JpaPlusAutoConfiguration.SELF = self;
    }

    @Bean
    @Lazy(value = false)
    @ConditionalOnClass(JpaPlusAutoConfiguration.class)
    public ReturnPropsSetNullProcessing returnPropsSetNullProcessing() {
        return new ReturnPropsSetNullProcessing();
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
}
