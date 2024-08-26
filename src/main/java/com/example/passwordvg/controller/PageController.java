package com.example.passwordvg.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


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
}
