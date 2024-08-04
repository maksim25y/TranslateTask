package ru.mudan.translatetask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mudan.translatetask.service.TranslateService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TranslateTaskApplicationTests {

    @Autowired
    private TranslateService translateService;
    private String translatedText;

    @Test
    void testTranslateFromEnToFr() {
        translatedText = translateService.result("Hello", "en", "fr");
        assertEquals("Bonjour", translatedText);
    }
    @Test
    void testTranslateFromRuToEn() {
        translatedText = translateService.result("Привет", "ru", "en");
        assertEquals("Hello", translatedText);
    }
    @Test
    void testTranslateWithBadCountry() {
        assertThrows(RuntimeException.class,() ->translateService.result("Привет", "ru", "some"));
    }
    @Test
    void testLanguagesContainsRussia() {
        assertTrue(translateService.containsLanguage("ru"));
    }
    @Test
    void testLanguagesContainsSpain() {
        assertTrue(translateService.containsLanguage("es"));
    }
    @Test
    void testLanguagesContainsEngland() {
        assertTrue(translateService.containsLanguage("en"));
    }
    @Test
    void testLanguagesDoesNotContainsBadCountry() {
        assertFalse(translateService.containsLanguage("bad"));
    }


}
