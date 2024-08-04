package ru.mudan.translatetask.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
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
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping("/translate")
public class TranslateController {
    private static final Logger LOGGER = Logger.getLogger(TranslateController.class);
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
                       HttpServletResponse response,
                       @RequestParam("q")String q,
                       @RequestParam("source")String source,
                       @RequestParam("target")String target) throws InterruptedException {
        if(!translateService.containsLanguage(source) || !translateService.containsLanguage(target)){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            model.addAttribute("error","Не найден язык исходного сообщения");
            return "views/index";
        }
        String[] wordsInPhrase = q.split(" ");
        String[]translatedWords = new String[wordsInPhrase.length];
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(wordsInPhrase.length);
        AtomicBoolean hasError = new AtomicBoolean(false);
        for(int i=0;i<wordsInPhrase.length;i++){
            int index = i;
            executorService.execute(() -> {
                    try {
                        String translatedWord = translateService.result(wordsInPhrase[index], source, target);
                        translatedWords[index] = translatedWord;
                    }catch (Exception e){
                        hasError.set(true);
                        LOGGER.error("Translation error");
                        executorService.shutdownNow();
                    }finally {
                        latch.countDown();
                    }
            });
        }
        latch.await();
        if (hasError.get()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            model.addAttribute("error", "Ошибка доступа к ресурсу перевода");
            return "views/index";
        }
        String translatedPhrase = String.join(" ", translatedWords);
        String ipAddress = request.getRemoteAddr();
        resultService.save(ipAddress,q,translatedPhrase);
        model.addAttribute("response",translatedPhrase);
        return "views/index";
    }
}
