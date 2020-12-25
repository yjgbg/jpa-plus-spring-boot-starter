package com.github.yjgbg.jpa.plus.specification.support;

import com.github.yjgbg.jpa.plus.config.JpaPlusAutoConfiguration;
import com.github.yjgbg.jpa.plus.utils.Getter;
import lombok.val;

import javax.persistence.EntityGraph;
import java.util.Arrays;

public interface EntityGraphSupport<Self, T> {
    Class<T> getDomainClass();

    Self setEntityGraph(EntityGraph<T> entityGraph);

    default Self eager(String... props) {
        val em = JpaPlusAutoConfiguration.SELF.getEntityManager();
        val domainClass = getDomainClass();
        val entityGraph = em.createEntityGraph(domainClass);
        entityGraph.addAttributeNodes(props);
        return setEntityGraph(entityGraph);
    }

    private Self eagerArray(Getter<?, ?>... props) {
        return eager(Arrays.stream(props).map(Getter::propertyName).toArray(String[]::new));
    }

    default Self eager(Getter<T, ?> prop0) {
        return eagerArray(prop0);
    }

    default Self eager(Getter<T, ?> prop0, Getter<T, ?> prop1) {
        return eagerArray(prop0, prop1);
    }

    default Self eager(Getter<T, ?> prop0, Getter<T, ?> prop1, Getter<T, ?> prop2) {
        return eagerArray(prop0, prop1, prop2);
    }

    default Self eager(Getter<T, ?> prop0, Getter<T, ?> prop1, Getter<T, ?> prop2,
                       Getter<T, ?> prop3) {
        return eagerArray(prop0, prop1, prop2, prop3);
    }

    default Self eager(Getter<T, ?> prop0, Getter<T, ?> prop1, Getter<T, ?> prop2,
                       Getter<T, ?> prop3, Getter<T, ?> prop4) {
        return eagerArray(prop0, prop1, prop2, prop3, prop4);
    }

    default Self eager(Getter<T, ?> prop0, Getter<T, ?> prop1, Getter<T, ?> prop2,
                       Getter<T, ?> prop3, Getter<T, ?> prop4, Getter<T, ?> prop5) {
        return eagerArray(prop0, prop1, prop2, prop3, prop4, prop5);
    }

    default Self eager(Getter<T, ?> prop0, Getter<T, ?> prop1, Getter<T, ?> prop2,
                       Getter<T, ?> prop3, Getter<T, ?> prop4, Getter<T, ?> prop5,
                       Getter<T, ?> prop6) {
        return eagerArray(prop0, prop1, prop2, prop3, prop4, prop5, prop6);
    }

    default Self eager(Getter<T, ?> prop0, Getter<T, ?> prop1, Getter<T, ?> prop2,
                       Getter<T, ?> prop3, Getter<T, ?> prop4, Getter<T, ?> prop5,
                       Getter<T, ?> prop6, Getter<T, ?> prop7) {
        return eagerArray(prop0, prop1, prop2, prop3, prop4, prop5, prop6, prop7);
    }

}
