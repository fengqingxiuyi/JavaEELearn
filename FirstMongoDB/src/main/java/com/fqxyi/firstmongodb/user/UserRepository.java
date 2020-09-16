package com.fqxyi.firstmongodb.user;

import com.fqxyi.firstmongodb.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ShenBF
 * @desc 实现User的数据访问对象：UserRepository
 * @date 2018/8/1
 */
public interface UserRepository extends MongoRepository<User, Long> {

    User findByUsername(String username);

    User findByAge(int age);

}
