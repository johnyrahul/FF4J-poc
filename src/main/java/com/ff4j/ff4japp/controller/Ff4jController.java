package com.ff4j.ff4japp.controller;

import org.ff4j.FF4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ff4j.ff4japp.service.Ff4jService;

@RestController
public class Ff4jController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ff4jController.class);



    @Autowired
    public FF4j ff4j;

    @Autowired
    @Qualifier("oldService")
    private Ff4jService ff4jService;

    @GetMapping("/")
    public String index() {

        if (ff4j.check("AwesomeFeature")) {
            return "Feature  enabled";
        }

        return "Feature not enabled";
    }

    @GetMapping("/aop")
    public String aop() {

       return ff4jService.sayHello("test");
    }

    @PostMapping("/testPost")
    public String testPost() {

        if (ff4j.check("AwesomeFeature")) {
            return "Feature  enabled";
        }

        return "Feature not enabled";
    }

}
