package com.example.passwordvg.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class PageController {

    @GetMapping("/")
    public String redirect(){
        return "redirect:/login";
    }
    
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/crack")
    public String crack() {
        return "crackPassword";
    }

    @PostMapping("/hash")
    public String hash(){
        return "home";
    }

    @PostMapping("/password")
    public String password(){
        return "crackPassword";
    }
}
