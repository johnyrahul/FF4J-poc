package com.ff4j.ff4japp.service;

import org.ff4j.aop.Flip;

public interface Ff4jService {

    @Flip(name = "newService", alterBean = "newService")
    String sayHello(String name);

}
