package com.jsn.nifty.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Service
public class MyService {

    private final RestTemplate restTemplate;

    @Autowired
    public MyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchDataFromExternalService() {
        String url = "http://api.weatherapi.com/v1/forecast.json?key=VIjay_JSN_09052025&q=London&days=1&aqi=no&alerts=no";
        return restTemplate.getForObject(url, String.class);
    }
}

