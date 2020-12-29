package com.github.yjgbg.jpa.plus.utils;

import com.google.common.base.CaseFormat;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * 反射工具包
 *
 * @author yjgbg
 */
@Slf4j
@UtilityClass
public class ReflectUtils {

  /**
   * 根据属性，获取get方法
   *
   * @param obj 对象
   * @param fieldName 属性名
   * @return return
   */
  @SneakyThrows
  private Object getValue0(Object obj, String fieldName) {
    if (obj == null) return null;
    val getMethodName  = "get"+CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,fieldName);
    return obj.getClass().getMethod(getMethodName).invoke(obj);
  }

  @SneakyThrows
  private void setValue0(Object obj,String fieldName,Object value) {
    // 如果以上情况都不满足，那么set Value
    val setMethodName = "set" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, fieldName);
    val method = Arrays.stream(obj.getClass().getMethods())
            .filter(m -> Objects.equals(m.getName(), setMethodName))
            .filter(m -> m.getParameterCount() == 1)
            .findAny().orElseThrow(NoSuchMethodException::new);
    method.invoke(obj, value);
  }

  /**
   * 设置属性的值，支持用点分隔，从而设置属性对象的属性的值
   * 如参数(obj,"field1.subfield2",3)将执行代码：
   *     obj.getField1().setSubfield2(3);
   * @param obj 目标对象
   * @param path 目标属性的路径
   * @param value 值
   */
  @SneakyThrows
  @SuppressWarnings({"rawtypes","unchecked"})
  public void setValue(@NonNull Object obj, String path, Object value) {
    log.debug("setValue(obj,{},{})",path,value);
    if (obj==null) return; // 如果obj为空，直接返回
    // 如果obj是集合，那么直接穿透，对每个元素递归该函数
    if (obj instanceof Iterable) ((Iterable<?>) obj).forEach(item -> setValue(item, path, value));
    if (obj instanceof Iterable) return;
    val arr = path.split("\\.");
    val isMap = obj instanceof Map;
    val lenEq1 = arr.length == 1;
    // 按照path中是否包含点和obj是否为map，正交分为4种情况
    if (isMap && lenEq1) {
      ((Map) obj).put(path, value);
    } else if (isMap) {
      // 并且path中没有点，那么直接put即可
      // 如果path中有点，那么get到值，并递归该函数
      val subObj = ((Map<?, ?>) obj).get(arr[0]);
      val subPath = path.replace(arr[0]+".","");
      setValue(subObj,subPath,value);
    } else if (lenEq1) {
      // 如果以上情况都不满足，那么set Value
      setValue0(obj,path,value);
    } else {// 如果存在点，那么get到值之后递归该函数
      val subObj = getValue0(obj,arr[0]);
      val subPath = path.replace(arr[0]+".","");
      setValue(subObj,subPath,value);
    }
  }
}
