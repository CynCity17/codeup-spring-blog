package com.codeup.codeupspringblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello from Spring";
    }

    @GetMapping("/hello/{name}")
    @ResponseBody
    public String sayHello(@PathVariable String name){
        return "Hello " + name + "!";
    }

    @GetMapping("/hello/{name1}/and/{name2}")
    @ResponseBody
    public String sayHello2(@PathVariable String name1, @PathVariable String name2){
        return name1 + " says hello to " + name2 + "!";
    }
}
