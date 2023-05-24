package com.codeup.codeupspringblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MathController {

    @GetMapping("/add/{num1}/and/{num2}")
    @ResponseBody
    public String addition(@PathVariable int num1, @PathVariable int num2){
        return String.valueOf(num1 + num2);
    }

    @GetMapping("/subtract/{num1}/and/{num2}")
    @ResponseBody
    public String subtraction(@PathVariable int num1, @PathVariable int num2){
        return String.valueOf(num1 - num2);
    }

    @GetMapping("/multiply/{num1}/and/{num2}")
    @ResponseBody
    public String multiplication(@PathVariable int num1, @PathVariable int num2){
        return String.valueOf(num1 + num2);
    }

    @GetMapping("/divide/{num1}/and/{num2}")
    @ResponseBody
    public String division(@PathVariable int num1, @PathVariable int num2){
        return String.valueOf(num1 / num2);
    }

}
