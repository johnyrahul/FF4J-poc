package com.ff4j.ff4japp.service;

import org.springframework.stereotype.Component;

@Component("oldService")
public class OldFf4jService implements Ff4jService{

    @Override
    public String sayHello(String name) {
        // TODO Auto-generated method stub
        return "Old Hello world";
    }
    
}
