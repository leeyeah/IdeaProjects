package com.leeyeah.demomvn.controller;

import com.leeyeah.demomvn.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class RestApiController {

    @RequestMapping("/stu")
    public ResponseEntity<Student > student(){
        Student obj = new Student();
        obj.setName("rest11");
        obj.setAge(22);


        return new ResponseEntity<Student>(obj, HttpStatus.OK);

    }

}
