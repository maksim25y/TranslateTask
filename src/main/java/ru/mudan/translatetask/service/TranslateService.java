package ru.mudan.translatetask.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TranslateService {
    private static final String TRANSLATE_API = "https://deep-translate1.p.rapidapi.com/language/translate/v2";
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private final ObjectMapper mapper;
    private final Gson gson;
    public TranslateService() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-rapidapi-host","deep-translate1.p.rapidapi.com");
        headers.add("x-rapidapi-key","eb2dd167ccmsh772cceedd9a5dc5p1c726fjsnf62f69f87a3d");
        mapper = new ObjectMapper();
        gson = new GsonBuilder().create();
        restTemplate = new RestTemplate();
    }
    public String result(String q, String source, String target){
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("q", q);
        requestBody.put("source", source);
        requestBody.put("target", target);
        String jsonString;
        try {
            jsonString = mapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
        var a = restTemplate.exchange(
                TRANSLATE_API,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        String response = a.getBody();
        TranslationResponse responseFromApi = gson.fromJson(response, TranslationResponse.class);
        return responseFromApi.getData().getTranslations().getTranslatedText();
    }
    @Setter
    @Getter
    public static class TranslationResponse {

        private Data data;
        @Setter
        @Getter
        public static class Data {
            private Translations translations;
        }
        @Setter
        @Getter
        public static class Translations {
            private String translatedText;
        }
    }
}
