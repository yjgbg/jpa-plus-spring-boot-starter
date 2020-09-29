package life.yjgbg.jpa.plus.utils;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Sets;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
@UtilityClass
public class ReflectUtils {

  /**
   * 根据属性，获取get方法
   *
   * @param ob 对象
   * @param fieldName 属性名
   * @return return
   */
  @SneakyThrows
  private Object getValue0(Object ob, String fieldName) {
    if (ob == null) return null;
    val getMethodName  = "get"+CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,fieldName);
    return ob.getClass().getMethod(getMethodName).invoke(ob);
  }

  /**
   * 根据属性名，拿到set方法，并把值set到对象中
   *
   * @param obj 对象
   * @param value value
   */
  @SneakyThrows
  private void setValue0(Object obj, String fieldName, Object value) {
    if (obj == null) return;
    val setMethodName  = "set"+CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,fieldName);
    obj.getClass().getMethod(setMethodName).invoke(value);
  }

  private void setValue(Collection<?> coll, String name, Object value) {
    log.debug("setValue(coll,{},{})",name,value);
    coll.forEach(item -> {
      if (item!=null) setValue(item, name, value);
    });
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private void setValue(Map map, String path, Object value) {
    log.debug("setValue(map,{},{})",path,value);
    val arr = path.split("\\.");
    if (arr.length==1) map.put(path, value);
    else setValue(map.get(arr[0]),path.substring(arr[0].length()+1),value);
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
  @SuppressWarnings("rawtypes")
  public void setValue(@NonNull Object obj, String path, Object value) {
    log.debug("setValue(obj,{},{})",path,value);
    if (obj instanceof Collection) {
      setValue((Collection<?>) obj, path, value);
      return;
    }
    if (obj instanceof Map) {
      setValue((Map) obj, path, value);
      return;
    }
    val arr = path.split("\\.");
    val lastIndex = arr.length - 1;
    val parent = Arrays.stream(arr, 0, lastIndex)
        .reduce(obj, ReflectUtils::getValue0, (oa, ob) -> null);
    setValue0(parent, arr[lastIndex], value);
  }

  /**
   * 根据path路径获取属性的值，
   * 如参数(obj,"field1.subfield2")将执行代码：obj.getField1().getField2();
   * @param obj 目标对象
   * @param path 目标属性的路径
   * @return 目标属性的值
   */
  @SneakyThrows
  public Object getValue(@NonNull Object obj, String path) {
    return Arrays.stream(path.split("\\."))
        .reduce(obj, ReflectUtils::getValue0, (oa, ob) -> null);
  }

  /**
   * 查询到类c的父类或接口中rawType为类clazz的泛型类
   * 类c为clazz的子类，并且clazz带泛型，于是此方法为查询到泛型父类
   * @param c 子类的类对象
   * @param clazz 父类或接口的类对象
   * @return 父类或接口的泛型类型
   */
  public ParameterizedType getTargetGenericParent(Class<?> c,Class<?> clazz) {
    val geneSuperClass = c.getGenericSuperclass();
    val geneSuperInterfaces = c.getGenericInterfaces();
    return Stream.of(new Type[]{geneSuperClass},geneSuperInterfaces)
        .flatMap(Arrays::stream)
        .filter(geneType -> geneType instanceof ParameterizedType)
        .map(geneType -> (ParameterizedType)geneType)
        .filter(geneType -> geneType.getRawType()==clazz)
        .findAny()
        .orElse(null);
  }
  /**
   * 获取目标类的所有属性（包括从父类继承的）
   * @param clazz 目标类
   * @return 属性集
   */
  public Set<Field> getAllFields(Class<?> clazz) {
    if (clazz==Object.class) return Sets.newHashSet();
    val fields = Sets.newHashSet(clazz.getDeclaredFields());
    val fieldsInParent = getAllFields(clazz.getSuperclass());
    return Sets.union(fields,fieldsInParent);
  }
}
