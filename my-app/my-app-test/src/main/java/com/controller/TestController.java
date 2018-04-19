package com.controller;

import com.test.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    @RequestMapping("/index")
    public String toIndex(){
        System.out.println("ok");
        return "index.jsp";
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.test();
    }


}
