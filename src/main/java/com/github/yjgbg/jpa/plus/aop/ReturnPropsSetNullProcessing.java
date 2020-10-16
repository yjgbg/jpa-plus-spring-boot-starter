package com.github.yjgbg.jpa.plus.aop;

import com.github.yjgbg.jpa.plus.utils.ReflectUtils;
import java.util.Arrays;
import lombok.SneakyThrows;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class ReturnPropsSetNullProcessing {
  @SneakyThrows
  @Around("@annotation(com.github.yjgbg.jpa.plus.aop.ReturnPropsSetNull)")
  public Object send(ProceedingJoinPoint joinPoint) {
    val res = joinPoint.proceed(joinPoint.getArgs());
    if (res==null) return null;
    val methodSig = (MethodSignature) joinPoint.getSignature();
    val propsSetNull = methodSig.getMethod()
        .getAnnotation(ReturnPropsSetNull.class);
    Arrays.stream(propsSetNull.value())
        .forEach(prop -> ReflectUtils.setValue(res, prop, null));
    return res;
  }
}