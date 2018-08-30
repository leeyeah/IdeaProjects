package com.leeyeah.ua.service;

import org.springframework.stereotype.Service;

@Service
public class StudentBusiness implements IUserBusiness {
    @Override
    public String getName() {
        return "StudentBusiness.getName";
    }
}
