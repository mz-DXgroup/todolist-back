package com.example.todolist.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/docs")
@Controller
public class SwaggerController {

    @GetMapping("/restdocs")
    public String restdocs() {
        return "forward:/restdocs/index.html";
    }

    @GetMapping("/swagger")
    public String swagger() {
        return "redirect:/swagger/index.html";
    }
}
