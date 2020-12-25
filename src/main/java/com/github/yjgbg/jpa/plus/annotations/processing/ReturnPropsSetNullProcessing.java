package com.github.yjgbg.jpa.plus.annotations.processing;

import com.github.yjgbg.jpa.plus.annotations.ReturnPropsSetNull;
import com.github.yjgbg.jpa.plus.utils.ReflectUtils;
import lombok.SneakyThrows;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Arrays;

/**
 * 对 下面这个注解功能的实现
 *
 * @see ReturnPropsSetNull
 */
@Aspect
public class ReturnPropsSetNullProcessing {
  @SneakyThrows
  @Around("@annotation(com.github.yjgbg.jpa.plus.annotations.ReturnPropsSetNull)")
  public Object send(ProceedingJoinPoint joinPoint) {
    val res = joinPoint.proceed(joinPoint.getArgs());
    if (res == null) return null;
    val methodSig = (MethodSignature) joinPoint.getSignature();
    val propsSetNull = methodSig.getMethod()
            .getAnnotation(ReturnPropsSetNull.class);
    Arrays.stream(propsSetNull.value())
            .forEach(prop -> ReflectUtils.setValue(res, prop, null));
    return res;
  }
}