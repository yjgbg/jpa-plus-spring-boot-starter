package com.github.yjgbg.jpa.plus.specificationSupport;

import com.github.yjgbg.jpa.plus.JpaPlusSupport;
import lombok.val;

import javax.persistence.EntityGraph;

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
}
