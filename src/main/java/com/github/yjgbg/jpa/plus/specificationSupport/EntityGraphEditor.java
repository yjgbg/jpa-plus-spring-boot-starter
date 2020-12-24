package com.github.yjgbg.jpa.plus.specificationSupport;

import com.github.yjgbg.jpa.plus.JpaPlusSupport;
import com.github.yjgbg.jpa.plus.utils.Getter;
import lombok.val;

import javax.persistence.EntityGraph;
import java.util.Arrays;

public interface EntityGraphEditor<Self,T> {
    Class<T> getDomainClass();
    Self setEntityGraph(EntityGraph<T> entityGraph);

    default Self eager(String... props) {
        val em = JpaPlusSupport.SELF.getEntityManager();
        val domainClass = getDomainClass();
        val entityGraph = em.createEntityGraph(domainClass);
        entityGraph.addAttributeNodes(props);
        return setEntityGraph(entityGraph);
    }

    @SuppressWarnings("unchecked")
    default Self eager(Getter<T,?>... props) {
        return eager(Arrays.stream(props).map(Getter::propertyName).toArray(String[]::new));
    }
}
