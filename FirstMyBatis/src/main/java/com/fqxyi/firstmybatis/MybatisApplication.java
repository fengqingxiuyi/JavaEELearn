package com.fqxyi.firstmybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 创建Spring Boot主类
 */

/**
 * 本地
 */

//@SpringBootApplication
//public class MybatisApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(MybatisApplication.class, args);
//	}
//}

/**
 * 部署
 */

@ServletComponentScan
@SpringBootApplication
public class MybatisApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MybatisApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MybatisApplication.class, args);
	}
}
