package com.leeyeah.map;

import com.leeyeah.model.AutoCar;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

public interface AutoCarMapper {

    @Delete("delete from autocar where carid=#{carid}")
    public void deleteCar(Integer carid);

    public AutoCar getCar(Integer carid);

    public void saveCar(AutoCar car);

}
