package com.leeyeah.ua;

import com.leeyeah.ua.service.IUserBusiness;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "stu")
public class StudentController {

    @Resource()
    private IUserBusiness ub;


    @RequestMapping()
    public String Add(ModelMap modelMap) {

        String temp = ub.getName();
        System.out.println("UserBusiness.getName() " + temp);
        modelMap.addAttribute("sid","add");
        return "student/add";
    }

    @RequestMapping(value = "del")
    public String Del(ModelMap modelMap){
        modelMap.addAttribute("sid","del");
        return "student/add";
    }

    @RequestMapping(value = "edit")
    @ResponseBody
    public String edit(ModelMap modelMap){
        return "abcdef";
    }
}
