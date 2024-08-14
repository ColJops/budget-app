package com.budget.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BudgetController {

    @GetMapping("/")
    public String sayHello() {
        return "<b>Hello World!</b><br>You're backend is working!</br>";
    }
}
