package io.ylab.monitoring.app.springmvc.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class HelloController {

    @GetMapping(value = "/hello")
    public String hello() {
        return "{\"it_works\": true}";
    }
}
