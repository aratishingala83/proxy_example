package com.jsn.nifty.config2;

import org.springframework.stereotype.Service;

@Service
public class MyService2 {

    private final MyApiService2 apiService;

    public MyService2(MyApiService2 apiService) {
        this.apiService = apiService;
    }

    public String fetchDataFromThirdParty() {
        return apiService.getResource().getBody();
    }
}