package com.example.microservices.currencyexcahngeservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
    //@Retry(name = "default", fallbackMethod = "hardCodedResponse")
    @CircuitBreaker(name = "default", fallbackMethod = "hardCodedResponse")
    public String sampleApi(){
        logger.info("Sample api call received");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/dummy-url", String.class);
        return forEntity.getBody();

        //return "Success";
    }

    public String hardCodedResponse(Exception e){
        return "fallback-response";
    }
}
