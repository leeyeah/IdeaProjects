package com.leeyeah.demomvn.dao;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class ProductDriverManagerDataSouce extends DriverManagerDataSource {
    public ProductDriverManagerDataSouce(){
        this.setUsername("root");
        this.setPassword("chinabj.1");
//mybatiscfg.xml
    }
}
