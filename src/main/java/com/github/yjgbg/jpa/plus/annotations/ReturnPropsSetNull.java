package com.github.yjgbg.jpa.plus.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在被标记的函数返回的时候，将指定的键位置空，
 * 如:field1.subfield2 将执行代码：returnValue.getField1().setSubfield2(null);
 * 对 collection，map 有特殊处理
 * collection 将会被穿透， map 被当作对象( key 为属性名，value 为属性值)来处理
 * 用于在 json 序列化之前，去除掉不想展示给前端的属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ReturnPropsSetNull {
  String[] value();
}
