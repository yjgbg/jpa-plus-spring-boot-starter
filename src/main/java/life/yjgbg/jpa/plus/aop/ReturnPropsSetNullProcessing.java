package life.yjgbg.jpa.plus.aop;

import java.util.Arrays;
import life.yjgbg.jpa.plus.utils.ReflectUtils;
import lombok.SneakyThrows;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class ReturnPropsSetNullProcessing {
  @SneakyThrows
  @Around("@annotation(life.yjgbg.jpa.plus.aop.ReturnPropsSetNull)")
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