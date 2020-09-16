package com.fqxyi.firstmybatis.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@Mapper
public interface UserDao {

    int insertName(Map<String, Object> paramMap);

    int updateName(Map<String, Object> paramMap);

    String queryName(int id);

}
