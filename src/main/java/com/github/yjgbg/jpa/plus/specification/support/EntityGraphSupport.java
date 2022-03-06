package com.github.yjgbg.jpa.plus.specification.support;

import com.github.yjgbg.jpa.plus.config.JpaPlusConfiguration;

import javax.persistence.EntityGraph;
import javax.persistence.Subgraph;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface EntityGraphSupport<Self, T> {
    Class<T> getDomainClass();

    Self setEntityGraph(EntityGraph<T> entityGraph);

    private void processingAbstractGraph(Consumer<String[]> addAttributeNodes, Function<String, Subgraph<?>> addSubgraph, String... props) {
        final var direct = Arrays.stream(props)
                .filter(x -> !x.contains("."))
                .toArray(String[]::new);
        addAttributeNodes.accept(direct);
        // 根据第一个点之前的字符分组
        var map = Arrays.stream(props)
                .filter(x -> x.contains("."))
                .map(x -> {
                    final var index = x.indexOf('.');
                    final var prefix = x.substring(0, index);
                    final var suffix = x.substring(index + 1);
                    return new Object() {
                        final String pre = prefix;
                        final String suf = suffix;
                    };
                }).collect(Collectors.groupingBy(newObj -> newObj.pre));
        map.forEach((key, value) -> {
            final var strings = value.stream().map(newObj -> newObj.suf).toArray(String[]::new);
            final var subgraph = addSubgraph.apply(key);
            processingAbstractGraph(subgraph::addAttributeNodes, subgraph::addSubgraph, strings);
        });
    }

    private void processingEntityGraph(EntityGraph<T> entityGraph, String... props) {
        processingAbstractGraph(entityGraph::addAttributeNodes, entityGraph::addSubgraph, props);
    }

    default Self eager(String... props) {
        final var em = JpaPlusConfiguration.self().getEntityManager();
        final var domainClass = getDomainClass();
        final var entityGraph = em.createEntityGraph(domainClass);
        processingEntityGraph(entityGraph, props);
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
