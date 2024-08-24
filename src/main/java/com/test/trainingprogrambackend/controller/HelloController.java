package com.test.trainingprogrambackend.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "hello测试")
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
