package com.leeyeah.demomvn.dao;

import com.leeyeah.demomvn.mapper.WorkerMapper;
import com.leeyeah.demomvn.model.Worker;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@Repository
public class WorkerDaoImpl implements WorkerDao {


    @Override
    public Worker getOne(int wid) {

        SqlSession session = null;
        SqlSessionFactory factory;

        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        factory = (SqlSessionFactory) webApplicationContext.getBean("sqlSessionFactory");

        try {
            session = factory.openSession();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        WorkerMapper workerMapper = session.getMapper(WorkerMapper.class);
        Worker worker = workerMapper.getWorker(1);
        session.close();

        return worker;

    }

    @Override
    public void delOne(int wid) {

    }
}
