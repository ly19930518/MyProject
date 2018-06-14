package com.controller;

import com.dao.model.Dictionary;
import com.service.TestService;
import com.test.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class TestController {
    @Resource
    public TestService testService;

    @RequestMapping("/index")
    public String toIndex(){
        System.out.println("ok");
        Dictionary d = testService.test();
        if(d != null){
            System.out.println(d.toString());
        }else{
            System.out.println(" 空 ");
        }
        return "index.jsp";
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.test();
    }


}
