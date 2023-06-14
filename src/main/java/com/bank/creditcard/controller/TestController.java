package com.bank.creditcard.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test-token")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String string() {
        return "Hello Manger";
    }
}
