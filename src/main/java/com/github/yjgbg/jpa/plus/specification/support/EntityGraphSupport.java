package com.github.yjgbg.jpa.plus.specification.support;

import com.github.yjgbg.jpa.plus.config.JpaPlusConfiguration;
import lombok.val;

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
    val direct = Arrays.stream(props)
        .filter(x -> !x.contains("."))
        .toArray(String[]::new);
    addAttributeNodes.accept(direct);
    // 根据第一个点之前的字符分组
    var map = Arrays.stream(props)
        .filter(x -> x.contains("."))
        .map(x -> {
          val index = x.indexOf('.');
          val prefix = x.substring(0, index);
          val suffix = x.substring(index + 1);
          return new Object() {
            final String pre = prefix;
            final String suf = suffix;
          };
        }).collect(Collectors.groupingBy(newObj -> newObj.pre));
    map.forEach((key, value) -> {
      val strings = value.stream().map(newObj -> newObj.suf).toArray(String[]::new);
      val subgraph = addSubgraph.apply(key);
      processingAbstractGraph(subgraph::addAttributeNodes, subgraph::addSubgraph, strings);
    });
  }

  private void processingEntityGraph(EntityGraph<T> entityGraph, String... props) {
    processingAbstractGraph(entityGraph::addAttributeNodes, entityGraph::addSubgraph, props);
  }

  default Self eager(String... props) {
    val em = JpaPlusConfiguration.self().getEntityManager();
    val domainClass = getDomainClass();
    val entityGraph = em.createEntityGraph(domainClass);
    processingEntityGraph(entityGraph, props);
    return setEntityGraph(entityGraph);
  }

  private Self eagerArray(StaticMethodReferenceGetter<?, ?>... props) {
    return eager(Arrays.stream(props).map(StaticMethodReferenceGetter::propertyName).toArray(String[]::new));
  }

  default Self eager(StaticMethodReferenceGetter<T, ?> g0) {
    return eagerArray(g0);
  }

  default Self eager(StaticMethodReferenceGetter<T, ?> g0, StaticMethodReferenceGetter<T, ?> g1) {
    return eagerArray(g0, g1);
  }

  default Self eager(StaticMethodReferenceGetter<T, ?> g0, StaticMethodReferenceGetter<T, ?> g1,
                     StaticMethodReferenceGetter<T, ?> g2) {
    return eagerArray(g0, g1, g2);
  }

  default Self eager(StaticMethodReferenceGetter<T, ?> g0, StaticMethodReferenceGetter<T, ?> g1,
                     StaticMethodReferenceGetter<T, ?> g2, StaticMethodReferenceGetter<T, ?> g3) {
    return eagerArray(g0, g1, g2, g3);
  }

  default Self eager(StaticMethodReferenceGetter<T, ?> g0, StaticMethodReferenceGetter<T, ?> g1,
                     StaticMethodReferenceGetter<T, ?> g2, StaticMethodReferenceGetter<T, ?> g3,
                     StaticMethodReferenceGetter<T, ?> g4) {
    return eagerArray(g0, g1, g2, g3, g4);
  }

  default Self eager(StaticMethodReferenceGetter<T, ?> g0, StaticMethodReferenceGetter<T, ?> g1,
                     StaticMethodReferenceGetter<T, ?> g2, StaticMethodReferenceGetter<T, ?> g3,
                     StaticMethodReferenceGetter<T, ?> g4, StaticMethodReferenceGetter<T, ?> g5) {
    return eagerArray(g0, g1, g2, g3, g4, g5);
  }

  default Self eager(StaticMethodReferenceGetter<T, ?> g0, StaticMethodReferenceGetter<T, ?> g1,
                     StaticMethodReferenceGetter<T, ?> g2, StaticMethodReferenceGetter<T, ?> g3,
                     StaticMethodReferenceGetter<T, ?> g4, StaticMethodReferenceGetter<T, ?> g5,
                     StaticMethodReferenceGetter<T, ?> g6) {
    return eagerArray(g0, g1, g2, g3, g4, g5, g6);
  }

  default Self eager(StaticMethodReferenceGetter<T, ?> g0, StaticMethodReferenceGetter<T, ?> g1,
                     StaticMethodReferenceGetter<T, ?> g2, StaticMethodReferenceGetter<T, ?> g3,
                     StaticMethodReferenceGetter<T, ?> g4, StaticMethodReferenceGetter<T, ?> g5,
                     StaticMethodReferenceGetter<T, ?> g6, StaticMethodReferenceGetter<T, ?> g7) {
    return eagerArray(g0, g1, g2, g3, g4, g5, g6, g7);
  }

  default Self eager(StaticMethodReferenceGetter<T, ?> g0, StaticMethodReferenceGetter<T, ?> g1,
                     StaticMethodReferenceGetter<T, ?> g2, StaticMethodReferenceGetter<T, ?> g3,
                     StaticMethodReferenceGetter<T, ?> g4, StaticMethodReferenceGetter<T, ?> g5,
                     StaticMethodReferenceGetter<T, ?> g6, StaticMethodReferenceGetter<T, ?> g7,
                     StaticMethodReferenceGetter<T, ?> g8) {
    return eagerArray(g0, g1, g2, g3, g4, g5, g6, g7, g8);
  }

  default Self eager(StaticMethodReferenceGetter<T, ?> g0, StaticMethodReferenceGetter<T, ?> g1,
                     StaticMethodReferenceGetter<T, ?> g2, StaticMethodReferenceGetter<T, ?> g3,
                     StaticMethodReferenceGetter<T, ?> g4, StaticMethodReferenceGetter<T, ?> g5,
                     StaticMethodReferenceGetter<T, ?> g6, StaticMethodReferenceGetter<T, ?> g7,
                     StaticMethodReferenceGetter<T, ?> g8, StaticMethodReferenceGetter<T, ?> g9) {
    return eagerArray(g0, g1, g2, g3, g4, g5, g6, g7, g8, g9);
  }
}
