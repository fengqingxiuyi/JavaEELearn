# MyBatis

MyBatis的简单使用

#  参考文章

[什么是 MyBatis ？](http://www.mybatis.org/mybatis-3/zh/index.html)

# 在本机的MySQL中创建test库和user表

## 创建库

```
mysql> create database test;
Query OK, 1 row affected (0.00 sec) 输出这句话表示库创建成功
```

## 创建表

```
mysql> use test;
Database changed 需要先指定数据库才能创建表
mysql> create table user(
    -> id int not null primary key auto_increment,
    -> name char(20) not null,
    -> age int not null default '0');
Query OK, 0 rows affected (0.06 sec) 输出这句话表示表创建成功
```

# 编写代码

# 本地调试

比如：访问接口：<http://localhost:8080/queryName?id=1>，查看代码是否执行正确

# 部署到服务器

具体步骤请看这里：<https://github.com/fengqingxiuyi/FirstJavaWeb/blob/master/README.md>

# 远程调试

就是把本地调试的localhost替换为你服务器的ip地址，然后码是否执行正确