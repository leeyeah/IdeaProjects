package com.leeyeah.demomvn.mapper;

import com.leeyeah.demomvn.model.Worker;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

public interface WorkerMapper {
    @Select("select * from worker where wid=#{wid}")
    public Worker getWorker(int wid);

    @Delete("delete from worker where wid=#{wid}")
    public void delWorker(int wid);

    public void updateWorker(Worker worker);
}
