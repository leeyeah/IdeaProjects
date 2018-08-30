package com.leeyeah.demomvn.service;


import com.leeyeah.demomvn.dao.DAOException;
import com.leeyeah.demomvn.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;
    @Autowired
    private List<DAOException> list;

    public List<DAOException> getList() {
        return list;
    }

    public void setList(List<DAOException> list) {
        this.list = list;
    }

    @Override
    public String getUserName(String id) {
        return dao.getUserName(id) + "list " + list.size();
    }
}
