package com.leeyeah.demomvn.service;

import com.leeyeah.demomvn.model.Worker;

public interface WorkerService {

    public Worker getOne(int wid);

    public void delOne(int wid);
}
