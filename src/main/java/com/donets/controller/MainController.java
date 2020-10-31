package com.donets.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @PostMapping("/process")
    public String greeting() {
        return "index";
    }
}
