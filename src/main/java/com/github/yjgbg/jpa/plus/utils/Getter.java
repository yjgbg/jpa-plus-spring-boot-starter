package com.github.yjgbg.jpa.plus.utils;

import lombok.SneakyThrows;
import lombok.val;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunctionalInterface
public interface Getter<T, R> extends Function<T, R>, Serializable {
    @SneakyThrows
    private String propertyName0() {
        val method = this.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(Boolean.TRUE);
        val serializedLambda = (SerializedLambda) method.invoke(this);
        val getter =  serializedLambda.getImplMethodName();
        return Introspector.decapitalize(getter.replace("get", ""));
    }

    default String propertyName() {
        val clazz = getClass();
        val res0 = PackageCascade.GETTER_NAME_CACHE.get(clazz);
        if (res0 != null) return res0;
        val res = propertyName0();
        PackageCascade.GETTER_NAME_CACHE.put(clazz, res);
        return res;
    }

    private static String getters2Name(Getter<?, ?>... getters) {
        return Stream.of(getters).map(Getter::propertyName)
                .collect(Collectors.joining(PackageCascade.DELIMITER));
    }


    /**
     * LambdaUtils 函数s，接受多个SFunction(1参数，1返回值的lambda表达式),返回形似于aaa.bbb的字符串。
     * <p>
     * 必须使用实体的get方法，且必须为methodReference形式
     * 例如: s(Entity1::getName) : name ;
     * s(Entity1::getEntity2,Entity2::getEntity3,Entity3::getString) : entity2.entity3.string
     *
     * @param s0  A实体类中的get函数
     * @param <A> 实体类
     * @param <B> get函数返回值类型
     * @return 将get方法们对应的属性用点号连接起来的结果
     */
    static <A, B> String s(Getter<A, B> s0) {
        return getters2Name(s0);
    }

    static <A, B, C> String s(Getter<A, B> s0, Getter<B, C> s1) {
        return getters2Name(s0, s1);
    }

    static <A, B, C, D> String s(Getter<A, B> s0, Getter<B, C> s1, Getter<C, D> s2) {
        return getters2Name(s0, s1, s2);
    }

    static <A, B, C, D, E> String s(Getter<A, B> s0, Getter<B, C> s1, Getter<C, D> s2,
                                    Getter<D, E> s3) {
        return getters2Name(s0, s1, s2, s3);
    }

    static <A, B, C, D, E, F> String s(Getter<A, B> s0, Getter<B, C> s1, Getter<C, D> s2,
                                       Getter<D, E> s3, Getter<E, F> s4) {
        return getters2Name(s0, s1, s2, s3, s4);
    }

    static <A, B, C, D, E, F, G> String s(Getter<A, B> s0, Getter<B, C> s1, Getter<C, D> s2,
                                          Getter<D, E> s3, Getter<E, F> s4, Getter<F, G> s5) {
        return getters2Name(s0, s1, s2, s3, s4, s5);
    }

    static <T,R> Getter<T,R> c(Getter<T, Collection<R>> getter) {
        return new Getter<>() {
            @Override
            public R apply(T t) {
                return null;
            }

            @Override
            public String propertyName() {
                return getter.propertyName();
            }
        };
    }
}
