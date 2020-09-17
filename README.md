# JavaEELearn

Java后端学习

# 目录结构

```
JavaEELearn
  |--Chat //NIO实现的聊天室
  |--ChatNetty //Netty实现的聊天室
  |--FirstJavaWeb //JavaWeb入门
  |--FirstMongoDB //MongoDB入门
  |--FirstMyBatis //MyBatis入门
  |--FirstNetty //Netty入门
  |--FirstRedis //Redis入门
  |--SecondNetty //Netty实战
  |--README.md //文档总结
```

# 为Intellij IDEA使用指定的Maven

1、下载：<https://maven.apache.org/download.cgi>，选择`Binary zip archive`；

2、解压到指定目录，并添加环境变量，如：`export PATH="$PATH:/Users/fqxyi/Desktop/software/apache-maven-3.6.3/bin"`；

3、执行`mvn -v`命令校验是否安装正确；

4、为Intellij IDEA设置Maven，打开Preferences窗口，进入`Build, Execution, Deployment > Build Tools > Maven`：

4.1、修改`Maven home directory`为之前解压的目录，如：`/Users/fqxyi/Desktop/software/apache-maven-3.6.3`；

4.2、修改`User settings file`，如：`/Users/fqxyi/Desktop/software/apache-maven-3.6.3/conf/settings.xml`。

## 修改Maven配置

1、找到settings.xml，如：`/Users/fqxyi/Desktop/software/apache-maven-3.6.3/conf/settings.xml`；

2、编辑settings.xml文件，找到`mirrors`节点，在该节点中添加以下代码：

```xml
<!-- 访问阿里云的Maven仓库，加快下载速度 -->
<mirror>
  <id>nexus-aliyun</id>
  <mirrorOf>*,!jeecg,!jeecg-snapshots</mirrorOf>
  <name>Nexus aliyun</name>
  <url>http://maven.aliyun.com/nexus/content/groups/public</url>
</mirror>
```
