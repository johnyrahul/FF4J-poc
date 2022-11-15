package com.ff4j.ff4japp.service;

import org.springframework.stereotype.Component;

@Component("newService")
public class NewFf4jService implements Ff4jService{

    @Override
    public String sayHello(String name) {
        // TODO Auto-generated method stub
        return "New Hello world";
    }
    
}
