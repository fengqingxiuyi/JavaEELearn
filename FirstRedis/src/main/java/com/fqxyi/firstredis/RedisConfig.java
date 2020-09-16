package com.fqxyi.firstredis;

import com.fqxyi.firstredis.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author ShenBF
 * @desc 配置针对User的RedisTemplate实例
 * @date 2018/8/1
 */
/*
@Configuration : Spring Boot提倡基于Java的配置。尽管可以使用一个XML源来调用SpringApplication.run()，但官方建议使用@Configuration类作为主要源。
一般定义main方法的类也是主要@Configuration的一个很好候选。不需要将所有的@Configuration放进一个单独的类。@Import注解可以用来导入其他配置类。
另外也可以使用@ComponentScan注解自动收集所有的Spring组件，包括@Configuration类。
如果需要使用基于XML的配置，官方建议仍旧从一个@Configuration类开始。可以使用附加的@ImportResource注解加载XML配置文件。
@Configuration注解该类，等价与XML中配置beans；用@Bean标注方法等价于XML中配置bean。
 */
@Configuration
public class RedisConfig {

    @Bean //相当于XML中的,放在方法的上面，而不是类，意思是产生一个bean,并交给spring管理。
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, User> redisTemplate() {
        RedisTemplate<String, User> template = new RedisTemplate<String, User>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }

}
