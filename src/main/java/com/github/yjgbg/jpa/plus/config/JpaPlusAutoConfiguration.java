package com.github.yjgbg.jpa.plus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@SpringBootConfiguration
@ConditionalOnClass(JpaPlusAutoConfiguration.class)
public class JpaPlusAutoConfiguration {
    public static JpaPlusAutoConfiguration SELF;
    public final EntityManager entityManager;

    @Autowired
    public void injectSelf(JpaPlusAutoConfiguration self) {
        JpaPlusAutoConfiguration.SELF = self;
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
