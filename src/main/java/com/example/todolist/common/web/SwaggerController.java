package com.example.todolist.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SwaggerController {
    @GetMapping("/docs/swagger")
    public String swagger() {
        return "redirect:/swagger/index.html";
    }
}
