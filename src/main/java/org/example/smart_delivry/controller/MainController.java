package org.example.smart_delivry.controller;

import org.example.smart_delivry.service.HelloService;

public class MainController {
    private final HelloService helloService;
    public MainController(HelloService helloService){
        this.helloService = helloService;
    }
    public void run(){
        System.out.println(helloService.getMessage());
    }

}
