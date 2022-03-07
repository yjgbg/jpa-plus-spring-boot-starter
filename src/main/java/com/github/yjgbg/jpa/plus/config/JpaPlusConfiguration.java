package com.github.yjgbg.jpa.plus.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Getter
@RequiredArgsConstructor
@SpringBootConfiguration
@ConditionalOnClass(JpaPlusConfiguration.class)
public class JpaPlusConfiguration implements ApplicationContextAware {
    private static ApplicationContext APPLICATION_CONTEXT;
    public final EntityManager entityManager;

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

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
    }

    public static JpaPlusConfiguration self() {
        return APPLICATION_CONTEXT.getBean(JpaPlusConfiguration.class);
    }
}
