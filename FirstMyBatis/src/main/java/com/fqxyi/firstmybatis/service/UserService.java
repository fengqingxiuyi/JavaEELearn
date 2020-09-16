package com.fqxyi.firstmybatis.service;

public interface UserService {

    int insertName(int id, String name);

    int updateName(int id, String name);

    String queryName(int id);

}
