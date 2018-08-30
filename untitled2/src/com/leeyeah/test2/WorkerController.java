package com.leeyeah.test2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/")
public class WorkerController {

    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(){
        return "11111";
    }
}
