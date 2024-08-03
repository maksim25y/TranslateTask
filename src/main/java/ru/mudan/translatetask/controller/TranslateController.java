package ru.mudan.translatetask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/translate")
public class TranslateController {
    @GetMapping
    public String get()
    {
        return "views/index";
    }
}
