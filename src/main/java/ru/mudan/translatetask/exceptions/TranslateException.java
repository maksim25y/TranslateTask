package ru.mudan.translatetask.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class TranslateException extends RuntimeException {
    private final HttpStatusCode statusCode;

    public TranslateException(HttpStatusCode statusCode) {
        super("Failed with status code: " + statusCode.value());
        this.statusCode = statusCode;
    }

}