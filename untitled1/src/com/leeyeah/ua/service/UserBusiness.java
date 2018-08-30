package com.leeyeah.ua.service;

import org.springframework.stereotype.Service;

@Service
public class UserBusiness implements IUserBusiness {

    @Override
    public String getName() {
        return "UserBusiness.getName";
    }
}
