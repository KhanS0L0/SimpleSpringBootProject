package org.khan.solo.controllers;

import org.khan.solo.domain.User;
import org.khan.solo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    // todo: закончить "панель администратора" и управление пользователями
    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String userList(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "users/index";
    }

    @GetMapping("{id}")
    public String UserEdit(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        return "users/edit";
    }
}
