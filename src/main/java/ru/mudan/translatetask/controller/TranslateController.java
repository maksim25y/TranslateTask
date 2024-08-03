package ru.mudan.translatetask.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mudan.translatetask.service.ResultService;
import ru.mudan.translatetask.service.TranslateService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/translate")
public class TranslateController {
    private final TranslateService translateService;
    private final ResultService resultService;
    @Autowired
    public TranslateController(TranslateService translateService, ResultService resultService) {
        this.translateService = translateService;
        this.resultService = resultService;
    }

    @GetMapping
    public String get()
    {
        return "views/index";
    }
    @PostMapping
    public String send(Model model,
                       HttpServletRequest request,
                       @RequestParam("q")String q,
                       @RequestParam("source")String source,
                       @RequestParam("target")String target) throws InterruptedException {
        String[] wordsInPhrase = q.split(" ");
        String[]translatedWords = new String[wordsInPhrase.length];
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(wordsInPhrase.length);
        for(int i=0;i<wordsInPhrase.length;i++){
            int index = i;
            executorService.execute(() -> {
                    String a = translateService.result(wordsInPhrase[index], source, target);
                    translatedWords[index]=a;
                    System.out.println(Thread.currentThread().getName()+" "+wordsInPhrase[index]);
                    latch.countDown();
            });
        }
        latch.await();
        String translatedPhrase = String.join(" ", translatedWords);

        String ipAddress = request.getRemoteAddr();
        resultService.save(ipAddress,q,translatedPhrase);
        model.addAttribute("response",translatedPhrase);
        return "views/index";
    }
}
