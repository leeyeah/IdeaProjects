package com.leeyeah.quick;

import com.leeyeah.map.AutoCarMapper;
import com.leeyeah.model.AutoCar;
import com.mysql.cj.jdbc.ConnectionImpl;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.Reader;
import java.util.Date;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {


        System.out.println("DDDDDD");


        String temp ;
        String resource = "MyBatisCfg.xml";
        Reader reader = null;
        SqlSession session;
        reader = Resources.getResourceAsReader(resource);

        SqlSessionFactory factory = new SqlSessionFactoryBuilder()
                .build(reader);

        /*
        temp = factory.getConfiguration().getEnvironment().getDataSource().getConnection().toString();

        System.out.println("======+++=+====");
        System.out.println(temp);
        */
        session = factory.openSession();

        AutoCarMapper autoCarMapper = session.getMapper(AutoCarMapper.class);
        AutoCar newCar = new AutoCar();
        newCar.setColor("blue");
        newCar.setHeight(178);
        newCar.setProdDate(new Date(2008,1,1));
        autoCarMapper.saveCar(newCar);
        System.out.println(newCar.getCarID());
        //AutoCar car = autoCarMapper.getCar(1);
        //logger.info(car.getColor());

        //autoCarMapper.deleteCar(11);

        session.commit();
        session.close();





    }

}
