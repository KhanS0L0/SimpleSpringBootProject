package org.khan.solo.controllers;

import org.khan.solo.domain.Message;
import org.khan.solo.domain.User;
import org.khan.solo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @ModelAttribute
    public void addAttribute(Model model){
        model.addAttribute("message", new Message());
    }

    @GetMapping()
    public String showMessages(Model model){
        model.addAttribute("messages", messageRepository.findAll());
        return "messages/index";
    }

    @GetMapping("/{id}")
    public String showMessage(Model model, @PathVariable("id") int id){
        model.addAttribute("message", messageRepository.findById(id));
        return "messages/show";
    }

    @PostMapping()
    public String createMessage(@ModelAttribute("message") Message message,
                                @AuthenticationPrincipal User user){
        message.setUser(user);
        messageRepository.save(message);
        return "redirect:/messages";
    }

    @GetMapping("/filter")
    public String filterByTag(@RequestParam(name = "filter") String filter, Model model){
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }
        model.addAttribute("messages", messages);

        return "messages/index";
    }

}