# jpa-plus-spring-boot-starter

[![Join the chat at https://gitter.im/yjgbg/jpa-plus](https://badges.gitter.im/yjgbg/jpa-plus.svg)](https://gitter.im/yjgbg/jpa-plus?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

description
## User Guide
从maven中引入包，替换掉spring-boot-starter-data-jpa即可,
功能为spring-boot-starter-data-jpa的超集,
自带了乐观锁和悲观锁，以及逻辑删除功能
```xml
<dependency>
    <groupId>com.github.yjgbg</groupId>
    <artifactId>jpa-plus-spring-boot-starter</artifactId>
    <version>2.3.4.1</version>
</dependency>
```

重要类：```StdEntity```,标准实体基类，实现了ActiveEntity，
有save和remove，以及removeLogically,undoRemoveLogically方法，
功能分别为将该实体，持久化到数据库，从数据库删除，逻辑删除，以及撤销逻辑删除

重要类:```ExecutableSpecification```, 对Jpa中的Specification的包装，
便捷地构造查询条件，以及执行查询，返回结果
重要类:```StdRepository```, ```StdEntity```