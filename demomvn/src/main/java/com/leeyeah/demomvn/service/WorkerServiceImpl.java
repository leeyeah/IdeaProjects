package com.leeyeah.demomvn.service;

import com.leeyeah.demomvn.dao.WorkerDao;
import com.leeyeah.demomvn.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    private WorkerDao workerDao;

    @Override
    public Worker getOne(int wid) {
        return workerDao.getOne(wid);
        //return null;
    }

    @Override
    public void delOne(int wid) {

    }
}
