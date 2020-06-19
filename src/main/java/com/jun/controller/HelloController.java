package com.jun.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:郁君辉
 * Date:2020-06-17 11:24
 * Description:<描述>
 */
@RestController
public class HelloController {

    @RequestMapping(value = "/hello",method = RequestMethod.POST)
    public String hello(){
        return "hello";
    }
}
