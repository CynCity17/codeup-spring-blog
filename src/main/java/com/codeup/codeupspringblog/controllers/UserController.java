package com.codeup.codeupspringblog.controllers;


import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

    private UserRepository usersDao;

    public UserController(UserRepository usersDao){
        this.usersDao = usersDao;
    }

    @GetMapping("/register")
    public String registration(){
        return "posts/register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam(name = "username") String username, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password){
        User user = new User(username, email, password);
        usersDao.save(user);
        return "redirect:/posts";
    }
}
