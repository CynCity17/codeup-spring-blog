package com.codeup.codeupspringblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CoffeeController {

    @GetMapping("/coffee")
    public String coffeePage(){
        return "coffee";
    }

    @GetMapping("/coffee")
    public String newsletterSignup(@RequestParam(name = "email") String email, Model model){
        model.addAttribute("email", email);
        return "coffee";
    }

    @GetMapping("/coffee/{roast}")
    public String favRoast(@PathVariable String roast, Model model){
        Coffee coffee = new Coffee(roast);
        if(roast.equals("dark")) {
            coffee.setOrigin("Sumatra");
        } else {
            coffee.setOrigin("Colombia");
        }
        model.addAttribute("roast", roast);
        return "coffee";
    }
}
