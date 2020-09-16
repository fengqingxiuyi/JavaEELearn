package com.fqxyi.firstmybatis.service.impl;

import com.fqxyi.firstmybatis.dao.UserDao;
import com.fqxyi.firstmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 事务解释：https://blog.csdn.net/u010002184/article/details/72886218
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED) //如果当前没有事务，就新建一个事务，如果已经存在一个事务，加入到这个事务中。这是最常见的选择。
    public int insertName(int id, String name) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("name", name);
        return userDao.insertName(paramMap);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateName(int id, String name) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("name", name);
        return userDao.updateName(paramMap);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String queryName(int id) {
        return userDao.queryName(id);
    }

}
