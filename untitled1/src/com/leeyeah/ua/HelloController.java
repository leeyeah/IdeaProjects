package com.leeyeah.ua;

//import org.springframework.ui.ModelMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/")
public class HelloController {

    //@Resource()
    //private UserBusiness ub;

    @RequestMapping(method = RequestMethod.GET)
    public String printHello(ModelMap model) {
        model.addAttribute("msg", "Spring MVC Hello World");
        //model.addAttribute("name", ub.getName());

        return "hello";
    }

    @RequestMapping(value = "abc/{name}/{age}", method = RequestMethod.GET)
    public String printHello2(ModelMap model, @PathVariable("name") String name, @PathVariable("age") int age) {


        model.addAttribute("name", name);
        model.addAttribute("age", age);

        return "hello2";

    }

    @RequestMapping(value = "result", method = RequestMethod.GET)
    public String result(ModelMap model, @RequestParam String name, @RequestParam int age) {

        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "result";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String addUser(ModelMap model) {
        model.addAttribute("name", "jingp");
        return "addUser";
    }

    @RequestMapping(value = "/saveuser", method = RequestMethod.GET)
    public String saveUser(ModelMap model) {

        User u = new User();
        u.setAge(100);
        u.setName("gag");

        Student stu2 = new Student();
        stu2.setId("s001");
        stu2.setSchool("sqingh");

        model.addAttribute("user", u);

        model.addAttribute("stu", stu2);

        return "saveUser";
    }

    @RequestMapping(value = "/deluser")
    public String delUser(ModelMap model) {
        User u = new User();
        u.setAge(100);
        u.setName("gag hello");
        model.addAttribute("u", u);
        return "deluser";
    }

    @RequestMapping("/adduserbshow")
    public String adduserbshow(ModelMap modelMap) {
        User obj = new User();
        obj.setName("lee");
        obj.setAge(23);
        modelMap.addAttribute("user", obj);
        return "adduserb";
    }


    @RequestMapping(value = "/adduserb", method = RequestMethod.POST)
    public String adduserb(User user) {
        System.out.println(user.getName());
        return "adduserb";
    }


}
