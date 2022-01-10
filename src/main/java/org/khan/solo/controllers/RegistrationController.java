package org.khan.solo.controllers;

import org.khan.solo.domain.Role;
import org.khan.solo.domain.User;
import org.khan.solo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String newUser(@ModelAttribute(name = "user") User user, Model model){
        model.addAttribute("user", user);
        return "/registration";
    }

    @PostMapping()
    public String addUser(@ModelAttribute(name = "user") User user, BindingResult bindingResult){
        User newUser = userRepository.findByUsername(user.getUsername());

        if(newUser != null && bindingResult.hasErrors()){
            return "/registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/login";
    }
}
