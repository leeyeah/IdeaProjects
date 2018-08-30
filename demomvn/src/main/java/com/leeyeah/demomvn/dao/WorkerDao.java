package com.leeyeah.demomvn.dao;

import com.leeyeah.demomvn.model.Worker;

public interface WorkerDao {

    public Worker getOne(int wid);

    public void delOne(int wid);

}
