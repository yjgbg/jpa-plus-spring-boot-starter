package com.github.yjgbg.jpa.plus.annotations;

import com.github.yjgbg.jpa.plus.repository.JpaSpecificationRepositoryImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.data.repository.query.QueryLookupStrategy;

import java.lang.annotation.*;

/**
 * 开启JpaPlus功能
 * <p>
 * 完全继承自EnableJpaRepositories，只是修改了repositoryBaseClass的默认值
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableJpaRepositories
public @interface EnableJpaPlusRepositories {
    @AliasFor(annotation = EnableJpaRepositories.class)
    String[] value() default {};

    @AliasFor(annotation = EnableJpaRepositories.class)
    String[] basePackages() default {};

    @AliasFor(annotation = EnableJpaRepositories.class)
    Class<?>[] basePackageClasses() default {};

    @AliasFor(annotation = EnableJpaRepositories.class)
    ComponentScan.Filter[] includeFilters() default {};

    @AliasFor(annotation = EnableJpaRepositories.class)
    ComponentScan.Filter[] excludeFilters() default {};

    @AliasFor(annotation = EnableJpaRepositories.class)
    String repositoryImplementationPostfix() default "Impl";

    @AliasFor(annotation = EnableJpaRepositories.class)
    String namedQueriesLocation() default "";

    @AliasFor(annotation = EnableJpaRepositories.class)
    QueryLookupStrategy.Key queryLookupStrategy() default QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND;

    @AliasFor(annotation = EnableJpaRepositories.class)
    Class<?> repositoryFactoryBeanClass() default JpaRepositoryFactoryBean.class;

    @AliasFor(annotation = EnableJpaRepositories.class)
    Class<?> repositoryBaseClass() default JpaSpecificationRepositoryImpl.class; // 修改了默认值

    @AliasFor(annotation = EnableJpaRepositories.class)
    String entityManagerFactoryRef() default "entityManagerFactory";

    @AliasFor(annotation = EnableJpaRepositories.class)
    String transactionManagerRef() default "transactionManager";

    @AliasFor(annotation = EnableJpaRepositories.class)
    boolean considerNestedRepositories() default false;

    @AliasFor(annotation = EnableJpaRepositories.class)
    boolean enableDefaultTransactions() default true;

    @AliasFor(annotation = EnableJpaRepositories.class)
    BootstrapMode bootstrapMode() default BootstrapMode.DEFAULT;
}
