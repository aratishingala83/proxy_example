package com.jsn.nifty.config2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface MyApiService2 {

    @GetExchange("/api/resource")
    ResponseEntity<String> getResource();

    @PostExchange("/api/resource")
    ResponseEntity<String> createResource(@RequestBody String body);
}
