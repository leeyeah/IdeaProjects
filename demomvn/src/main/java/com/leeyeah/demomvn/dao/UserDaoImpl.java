package com.leeyeah.demomvn.dao;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public String getUserName(String name) {
        return "UName "+name;
    }
}
