# SecondNetty

Netty实战

- [Netty入门与实战教程](cnblogs.com/lbhym/p/12753314.html)

# ChannelInboundHandlerAdapter 和 ChannelOutboundHandlerAdapter

ChannelInboundHandlerAdapter：一般就是事件（event），比如当有数据到来时，channel被激活时或者不可用时，下面介绍几个最常用的。

1. channelActive：通道激活时触发，当客户端connect成功后，服务端就会接收到这个事件，从而可以把客户端的Channel记录下来，供后面复用
2. channelRead：这个必须用啊，当收到对方发来的数据后，就会触发，参数msg就是发来的信息，可以是基础类型，也可以是序列化的复杂对象。
3. channelReadComplete：channelRead执行后触发
4. exceptionCaught：出错是会触发，做一些错误处理

ChannelOutboundHandlerAdapter：监听自己的IO操作，比如connect，bind等，在重写这个Adapter的方法时，记得执行super.xxxx，否则动作无法执行。

1. bind：服务端执行bind时，会进入到这里，我们可以在bind前及bind后做一些操作
2. connect：客户端执行connect连接服务端时进入

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