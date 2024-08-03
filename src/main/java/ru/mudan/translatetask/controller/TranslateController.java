package ru.mudan.translatetask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mudan.translatetask.service.TranslateService;

@Controller
@RequestMapping("/translate")
public class TranslateController {
    private final TranslateService translateService;
    @Autowired
    public TranslateController(TranslateService translateService) {
        this.translateService = translateService;
    }

    @GetMapping
    public String get()
    {
        return "views/index";
    }
    @PostMapping
    public String send(Model model,
                       @RequestParam("q")String q,
                       @RequestParam("source")String source,
                       @RequestParam("target")String target) {
        String response = translateService.result(q,source,target);
        model.addAttribute("response",response);
        return "views/index";
    }
}
