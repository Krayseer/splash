package ru.anykeyers.orderservice.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/user")
    public String user() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
