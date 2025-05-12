package com.jsn.nifty.config;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    MyService myService;

    public MyController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/api/data")
    public String getData() {
        // This is a simple endpoint that returns a string
        myService.fetchDataFromExternalService();
        return "Hello, this is data from MyController!";

    }
}
