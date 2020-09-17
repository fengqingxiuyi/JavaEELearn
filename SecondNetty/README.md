# SecondNetty

Netty实战

- [Netty入门与实战教程](cnblogs.com/lbhym/p/12753314.html)

# Lombok使用

1、在`pom.xml`文件中添加依赖：

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.12</version>
</dependency>
```

2、为数据Bean类添加`@Data`注解

3、为IntelIJ IDEA添加`Lombok Plugin`插件

4、重启IntelIJ IDEA之后，打开`Preferences`窗口，搜索`Annotation Processors`选项，勾选`Enable annotation processing`