# Protobuf

- [Protocol Buffers](https://developers.google.com/protocol-buffers)
- [protobuf-java-download](https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/)

# 第一次编译

1、Mac电脑安装Protobuf，命令：`brew install protobuf`。

2、根据以下命令编译：

```
//-I 后面是 proto 文件所在目录
//--java_out 后面是 java 文件存放地址
//最后一行是 proto 文件名称
Protobuf % protoc -I=proto --java_out=src SubscribeReq.proto
```