package com.github.yjgbg.jpa.plus.specification.support;

import lombok.SneakyThrows;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 表示一个javaBean中的get方法
 * 用途：在构造ExecutableSpecification对象的时候，希望对路径有类型安全的表达，而不是拼接字符串
 * <p>
 * 例如: Role role = new User().getRole();
 * Getter&lt;User,Role&gt; getter = User::getRole;
 * assert "role" == getter.propertyName();
 *
 * @param <A> javaBean的类型
 * @param <B> get方法返回的类型
 */
@FunctionalInterface
public interface Getter<A, B> extends Function<A, B>, Serializable {
    @SneakyThrows
    private String propertyName0() {
        final var method = this.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(Boolean.TRUE);
        final var serializedLambda = (SerializedLambda) method.invoke(this);
        final var getter = serializedLambda.getImplMethodName();
        return getter.startsWith("get") ? Introspector.decapitalize(getter.substring(3)):getter;
    }

    /**
     * 返回该get方法对应的属性的名称
     * @return 返回该get方法对应的属性的名称
     */
    default String propertyName() {
        final var clazz = getClass();
        final var res0 = PackageCascade.GETTER_NAME_CACHE.get(clazz);
        if (res0 != null) return res0;
        final var res = propertyName0();
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
     * 例如: s(Entity1::getName)== "name" ;
     * s(Entity1::getEntity2,Entity2::getEntity3,Entity3::getString) == "entity2.entity3.string"
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

    /**
     * 类型变形，覆盖掉apply函数，使之返回值恒为空null
     * 用于路径表达
     *
     * @param getter getter函数
     * @param <A>    getter的参数类型
     * @param <B>    getter的返回值类型
     * @return 返回新的getter
     */
    static <A, B> Getter<A, B> c(Getter<A, Collection<B>> getter) {
        return new Getter<>() {
            @Override
            public B apply(A t) {
                return null;
            }

            @Override
            public String propertyName() {
                return getter.propertyName();
            }
        };
    }
}

/**
 * 包级常量，不暴露到外部
 */
class PackageCascade {
    static String DELIMITER = ".";
    static HashMap<Class<?>, String> GETTER_NAME_CACHE = new HashMap<>();
}