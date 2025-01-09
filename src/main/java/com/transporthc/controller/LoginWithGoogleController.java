package com.transporthc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginWithGoogleController {
    @GetMapping("/")
    public String home(){
        return "hello";
    }

    @GetMapping("google")
    public String secure(){
        return "secured";
    }
}
