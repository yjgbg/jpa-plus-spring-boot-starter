# jpa-plus-spring-boot-starter

[![Join the chat at https://gitter.im/yjgbg/jpa-plus](https://badges.gitter.im/yjgbg/jpa-plus.svg)](https://gitter.im/yjgbg/jpa-plus?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## User Guide
从maven中引入包，替换掉spring-boot-starter-data-jpa,
然后在application类上加上注解：enableJpaPlusRepository。
使实体类实现activeEntity接口，repository继承StdRepository接口即可
功能为spring-boot-starter-data-jpa的超集,可以便捷的构造查询条件
```xml
<dependency>
    <groupId>com.github.yjgbg</groupId>
    <artifactId>jpa-plus-spring-boot-starter</artifactId>
    <version>2.1.3.007-SNAPSHOT</version>
</dependency>
```