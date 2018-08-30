package com.leeyeah.demomvn.controller;

import com.leeyeah.demomvn.dao.ProductDriverManagerDataSouce;
import com.leeyeah.demomvn.exception.ExcetionAA;
import com.leeyeah.demomvn.service.UserService;
import com.leeyeah.demomvn.model.Worker;
import com.leeyeah.demomvn.service.WorkerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.leeyeah.demomvn.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.*;

@Controller
@RequestMapping(value = "/hello")
public class Hello {
    private static final Logger logger = LogManager.getLogger(Hello.class);

    @Autowired
    private UserService userService;

    @Autowired
    private WorkerService workerService;

    @RequestMapping(value = "/welcome")
    public String welcome(ModelMap modelMap)throws Exception{


        modelMap.addAttribute("stuid",userService.getUserName("jim"));
        System.out.println("-------logger start--- ");
        logger.debug("a debug msg");
        logger.info("a info msg");
        logger.warn("a warn msg");
        logger.error("a error msg");
        logger.fatal("a fatal msg");
        System.out.println("-------logger End--- ");

        return "welcome";
    }

    @GetMapping(value = "abc")
    public String abc(ModelMap modelMap)throws Exception{
        return "abc";
    }

    @GetMapping(value = "abc2")
    public String abc2(ModelMap modelMap)throws Exception{
        //throw new Exception("1112333");

        throw new ExcetionAA("exceptionaa");
        //return "abc";
    }

    @RequestMapping("/stu")
    public @ResponseBody String getStu(){

        Student obj = new Student();
        obj.setAge(11);
        obj.setName("gggg");
        String json = "{\"name\":\"11\"}";



        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        Worker worker = webApplicationContext.getBean("workera", Worker.class);

        System.out.println(worker);

        Worker workerb = webApplicationContext.getBean("workera", Worker.class);

        System.out.println(workerb);



        //org.springframework.jdbc.datasource.DriverManagerDataSource


        return worker.getName()+" "+worker.getAge()+" "+worker.isFlag();
    }

    @RequestMapping("/w")
    public @ResponseBody String getWorker(){

        String temp = "";

        workerService.getOne(1);


        return temp;


    }

}
